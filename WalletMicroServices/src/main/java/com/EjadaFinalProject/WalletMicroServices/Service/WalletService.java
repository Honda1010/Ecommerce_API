package com.EjadaFinalProject.WalletMicroServices.Service;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.BalanceNotEnoughException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletAlreadyExistException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletAlreadyExistException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    public Wallets CreateWallet(int id){
        Wallets existingWallet = walletRepo.findByUserId(id);
        if (existingWallet != null) {
            throw  new WalletAlreadyExistException("This User with id: "+id+" Already have wallet"); // Wallet already exists for this user
        }
        // Create a new wallet for the user
        Wallets wallet = new Wallets();
        wallet.setUserId(id);
        return walletRepo.save(wallet);
    }
    public Wallets UpdateWallet(int id, Wallets wallet) {
        Wallets existingWallet = walletRepo.findByUserId(id);
        if (existingWallet == null) {
            throw new WalletAlreadyExistException("This User with id: "+id+" does not have a wallet to update");
        }
        existingWallet.setBalance(wallet.getBalance());
        existingWallet.setUserId(wallet.getUserId());
        return walletRepo.save(existingWallet);
    }
    public Wallets GetWalletByUserId(int id) {
        Wallets wallet = walletRepo.findByUserId(id);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + id);
        }
        return wallet;
    }
    public void DeleteWallet (int id){
        Wallets wallet = walletRepo.findByUserId(id);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + id);
        }
        walletRepo.delete(wallet);
    }
    public Wallets depositToWallet(int id, double amount){
        Wallets wallet = walletRepo.findByUserId(id);
        WalletTransaction walletTransaction = new WalletTransaction();
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + id);
        }
        // Create a new transaction record
        walletTransaction.setWallet(wallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setType("CREDIT");
        walletTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(walletTransaction);
        // Update the wallet balance
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepo.save(wallet);
    }
    public Wallets withdrawFromWallet(int id, double amount) {
        Wallets wallet = walletRepo.findByUserId(id);
        WalletTransaction walletTransaction = new WalletTransaction();
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + id);
        }
        if (wallet.getBalance() < amount) {
            throw new BalanceNotEnoughException("Balance in Wallet for UserId: "+id+" is not enough");// Insufficient funds
        }
        // Create a new transaction record
        walletTransaction.setWallet(wallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setType("DEBIT");
        walletTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(walletTransaction);
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepo.save(wallet);
    }
    public Wallets transferBetweenWallets(int fromUserId,int toUserId,double amount){
        Wallets fromWallet = walletRepo.findByUserId(fromUserId);
        Wallets toWallet = walletRepo.findByUserId(toUserId);
        WalletTransaction fromTransaction = new WalletTransaction();
        WalletTransaction toTransaction = new WalletTransaction();
        if (fromWallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: "+fromUserId);
        }
        if (toWallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: "+toUserId);
        }
        if (fromWallet.getBalance() < amount) {
            throw new BalanceNotEnoughException("Balance in Wallet for UserId: "+fromUserId+" is not enough");// Insufficient funds
        }
        // Create a new transaction record for the sender
        fromTransaction.setWallet(fromWallet);
        fromTransaction.setAmount(amount);
        fromTransaction.setType("DEBIT");
        fromTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(fromTransaction);
        // Create a new transaction record for the receiver
        toTransaction.setWallet(toWallet);
        toTransaction.setAmount(amount);
        toTransaction.setType("CREDIT");
        toTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(toTransaction);
        // Update the balances
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        walletRepo.save(fromWallet);
        return walletRepo.save(toWallet);
    }

}
