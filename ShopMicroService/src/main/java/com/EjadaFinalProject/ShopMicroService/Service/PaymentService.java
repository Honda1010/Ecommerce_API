package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions.OrderIsAlreadyCancelledException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions.OrderIsAlreadyPaidException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions.OrderNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.PymentException.NotCompletedPaymentCancelException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.PymentException.PaymentNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.WalletException.WalletServiceIsNotAvailableException;
import com.EjadaFinalProject.ShopMicroService.Model.Order.Order;
import com.EjadaFinalProject.ShopMicroService.Model.Order.OrderStatus;
import com.EjadaFinalProject.ShopMicroService.Model.Payment.Payment;
import com.EjadaFinalProject.ShopMicroService.Model.Payment.PaymentStatus;
import com.EjadaFinalProject.ShopMicroService.Proxy.WalletProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.OrderRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.PaymentRepo;
import com.EjadaFinalProject.ShopMicroService.Wrappers.WalletWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private WalletWrapper walletWrapper;
    @Autowired
    private OrderRepo orderRepo;
    @Transactional
    public Payment Pay(int userId, int orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found for order ID: " + orderId));
        if (order.getOrderStatus()== OrderStatus.CANCELLED) {
            throw new OrderIsAlreadyCancelledException("Cannot pay for a cancelled order.");
        }
        double amount = order.getTotalPrice();
        boolean paymentStatus = walletWrapper.checkBalanceForUser(userId, amount);
        Payment FoundedPayment = paymentRepo.findByOrderIdAndUserId(orderId, userId);

        if (FoundedPayment != null && FoundedPayment.getStatus() == PaymentStatus.FAILED) {
            FoundedPayment.setDate(LocalDateTime.now());
            if (!paymentStatus) {
                paymentRepo.save(FoundedPayment);
                throw new RuntimeException("Payment failed due to insufficient balance or Connection Error.");
            }
            walletWrapper.withdrawFromWalletForUser(userId, amount);
            FoundedPayment.setStatus(PaymentStatus.COMPLETED);
            order.setOrderStatus(OrderStatus.SHIPPED);
            orderRepo.save(order);
            return paymentRepo.save(FoundedPayment);
        }
        else if (FoundedPayment != null && FoundedPayment.getStatus() == PaymentStatus.COMPLETED) {
            throw new OrderIsAlreadyPaidException("This Order is Already Paid.");
        }else{
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setUserId(userId);
            payment.setAmount(amount);
            payment.setDate(LocalDateTime.now());
            if (!paymentStatus) {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepo.save(payment);
                throw new RuntimeException("Payment failed due to insufficient balance or Connection Error.");
            }
            String msg= walletWrapper.withdrawFromWalletForUser(userId, amount);
            if (msg==null){
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepo.save(payment);
                throw new WalletServiceIsNotAvailableException(" Wallet Service is not available. Cannot process payment at this time.");
            }
            payment.setStatus(PaymentStatus.COMPLETED);
            order.setOrderStatus(OrderStatus.SHIPPED);
            orderRepo.save(order);
            return paymentRepo.save(payment);
        }
    }
    @Transactional
    public void cancelPayment(int paymentId) {
        Payment payment = paymentRepo.findById(paymentId).orElseThrow(()-> new PaymentNotFoundException("Payment not found for payment ID: " + paymentId));
        Order order = orderRepo.findById(payment.getOrderId()).orElseThrow(()-> new OrderNotFoundException("Order not found for order ID: " + payment.getOrderId()));
        if (payment.getStatus() == PaymentStatus.FAILED || payment.getStatus() == PaymentStatus.CANCELLED) {
            throw new NotCompletedPaymentCancelException("Cannot cancel a payment that is not completed.");
        }
            String msg = walletWrapper.depositToWalletForUser(payment.getUserId(),payment.getAmount());
            if (msg == null) {
                throw new WalletServiceIsNotAvailableException("Wallet Service is not available. Cannot cancel payment at this time.");
            }
            payment.setStatus(PaymentStatus.CANCELLED);
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepo.save(order);
            paymentRepo.save(payment);
    }
    public List<Payment> findPaymentsByUserId(int userId) {
        return paymentRepo.findByUserId(userId);
    }
    public List<Payment> findAllPayments() {
        return  paymentRepo.findAll();
    }
    public void deletePayment(int paymentId) {
        paymentRepo.deleteById(paymentId);
    }

}
