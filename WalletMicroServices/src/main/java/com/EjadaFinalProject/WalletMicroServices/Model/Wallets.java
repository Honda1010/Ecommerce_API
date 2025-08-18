package com.EjadaFinalProject.WalletMicroServices.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Wallets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;
    @JsonIgnore
    private Integer userId;
    private double balance=0.0;

    public Wallets() {
    }

    public Wallets(Integer walletId, Integer userId, double balance) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
