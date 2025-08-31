package com.EjadaFinalProject.WalletMicroServices.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionType type;// "credit" or "debit"
    private double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "walletId",nullable = false)
    @JsonIgnore
    private Wallets wallet;
    @OneToOne(mappedBy = "walletTransaction",cascade = CascadeType.ALL)
    private Wallet_SnapShot snapShot;

    public WalletTransaction() {
    }

    public WalletTransaction(TransactionType type, double amount, LocalDateTime timestamp, Wallets wallet) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.wallet = wallet;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
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
    public Wallet_SnapShot getSnapShot() {
        return snapShot;
    }
    public void setSnapShot(Wallet_SnapShot snapShot) {
        this.snapShot = snapShot;
    }
}
