package com.EjadaFinalProject.WalletMicroServices.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Wallets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletId;
    private double balance=0.0;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WalletTransaction> transactions;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true)
    @JsonManagedReference
    @JsonIgnore
    private Users user;

    public List<WalletTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WalletTransaction> transactions) {
        this.transactions = transactions;
    }

    public Wallets() {
    }

    public Wallets(Integer walletId,Users User, double balance) {
        this.walletId = walletId;
        this.balance = balance;
        this.user=User;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
