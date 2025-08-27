package com.EjadaFinalProject.ShopMicroService.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer userId;
    @Column(unique = true)
    private Integer orderId;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime date;
    public Payment() {
    }
    public Payment(Integer id, Integer userId,int orderId, Double amount, PaymentStatus status, LocalDateTime date) {
        Id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
