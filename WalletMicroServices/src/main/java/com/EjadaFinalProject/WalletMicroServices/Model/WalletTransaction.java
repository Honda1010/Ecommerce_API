package com.EjadaFinalProject.WalletMicroServices.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private String type;// "credit" or "debit"
    private double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "walletId",nullable = false)
    private Wallets wallet;

    public WalletTransaction() {
    }

    public WalletTransaction(Integer transactionId, String type, double amount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Wallets getWallet() {
        return wallet;
    }

    public void setWallet(Wallets wallet) {
        this.wallet = wallet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
