package com.EjadaFinalProject.ShopMicroService.Controller;

import com.EjadaFinalProject.ShopMicroService.Model.Payment.Payment;
import com.EjadaFinalProject.ShopMicroService.Model.Payment.PaymentStatus;
import com.EjadaFinalProject.ShopMicroService.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("payments")
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makepayment/{userId}/{orderId}" )
    public ResponseEntity<String> makePayment(@PathVariable int userId, @PathVariable int orderId) {
        Payment payment= paymentService.Pay(userId,orderId);
        return ResponseEntity.ok("Payment of amount "+payment.getAmount()+" is "+ PaymentStatus.COMPLETED);
    }
    @PostMapping("/cancelpayment/{paymentId}" )
    public ResponseEntity<String> cancelPayment(@PathVariable int paymentId) {
        paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok("Payment with id "+paymentId+" is "+ PaymentStatus.CANCELLED);
    }
    @GetMapping("/{userId}" )
    public ResponseEntity<List<Payment>> getPaymentByUserId(@PathVariable int userId)
    {
        List<Payment> payment= paymentService.findPaymentsByUserId(userId);
        return ResponseEntity.ok(payment);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all" )
    public ResponseEntity<List<Payment>> getAllPayments()
    {
        List<Payment> payment= paymentService.findAllPayments();
        return ResponseEntity.ok(payment);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{paymentId}" )
    public ResponseEntity<String> deletePaymentById(@PathVariable int paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok("Payment with id " + paymentId + " is Deleted");
    }



}
