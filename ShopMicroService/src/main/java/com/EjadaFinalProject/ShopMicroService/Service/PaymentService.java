package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions.OrderIsAlreadyPaidException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.WalletException.WalletServiceIsNotAvailableException;
import com.EjadaFinalProject.ShopMicroService.Model.Payment.Payment;
import com.EjadaFinalProject.ShopMicroService.Model.Payment.PaymentStatus;
import com.EjadaFinalProject.ShopMicroService.Proxy.WalletProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.OrderRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.PaymentRepo;
import com.EjadaFinalProject.ShopMicroService.Wrappers.WalletWrapper;
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

    public Payment Pay(int userId, int orderId) {
        double amount = orderRepo.findById(orderId).get().getTotalPrice();
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
            return paymentRepo.save(payment);
        }
    }
    public void cancelPayment(int paymentId) {
        Payment payment = paymentRepo.findById(paymentId).get();
        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            String msg = walletWrapper.depositToWalletForUser(payment.getUserId(),payment.getAmount());
            if (msg == null) {
                throw new WalletServiceIsNotAvailableException("Wallet Service is not available. Cannot cancel payment at this time.");
            }
            payment.setStatus(PaymentStatus.CANCELLED);
            paymentRepo.save(payment);
        } else {
            throw new RuntimeException("Only completed payments can be cancelled.");
        }
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
