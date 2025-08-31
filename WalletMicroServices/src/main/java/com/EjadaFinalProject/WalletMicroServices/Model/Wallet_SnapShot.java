package com.EjadaFinalProject.WalletMicroServices.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Wallet_SnapShot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer walletId;
    private Double balance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transactionId")
    @JsonIgnore
    private WalletTransaction walletTransaction;

    public Wallet_SnapShot() {
    }

    public Wallet_SnapShot(Integer walletId, Double balance,WalletTransaction walletTransaction) {
        this.walletId = walletId;
        this.balance = balance;
        this.walletTransaction = walletTransaction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public WalletTransaction getWalletTransaction() {
        return walletTransaction;
    }
    public void setWalletTransaction(WalletTransaction walletTransaction) {
        this.walletTransaction = walletTransaction;
    }

}
