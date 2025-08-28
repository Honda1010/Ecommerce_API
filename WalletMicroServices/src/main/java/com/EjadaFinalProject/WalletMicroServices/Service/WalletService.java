package com.EjadaFinalProject.WalletMicroServices.Service;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.BalanceNotEnoughException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.UserNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletAlreadyExistException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.TransactionType;
import com.EjadaFinalProject.WalletMicroServices.Model.Users;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    public Wallets CreateWallet(int id){
        if(walletRepo.findByUser_UserId(id).isPresent()){
            throw new WalletAlreadyExistException("This User with id: "+id+" already has a wallet");
        }
        Wallets wallet = new Wallets();
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with id: "+id+" not found"));
        wallet.setUser(user);
        return walletRepo.save(wallet);
    }
    public Wallets UpdateWallet(int id, Wallets wallet) {
        Wallets existingWallet = walletRepo.findByUser_UserId(id).orElseThrow(()->new WalletNotFoundException("This User with id: "+id+" does not have a wallet to update"));
        existingWallet.setBalance(wallet.getBalance());
        Users user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with id: "+id+" not found"));
        wallet.setUser(user);
        return walletRepo.save(existingWallet);
    }
    public Wallets GetWalletByUserId(int id) {
        return walletRepo
                .findByUser_UserId(id)
                .orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + id));
    }
    public void DeleteWallet (int id){
        Wallets wallet = walletRepo.findByUser_UserId(id).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + id));
        walletRepo.delete(wallet);
    }
    public Wallets depositToWallet(int id, double amount){
        Wallets wallet = walletRepo.findByUser_UserId(id).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + id));
        // Create a new transaction record
        WalletTransaction walletTransaction = new WalletTransaction(TransactionType.DEPOSIT,amount, LocalDateTime.now(),wallet);
        transactionRepo.save(walletTransaction);
        // Update the wallet balance
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepo.save(wallet);
    }
    public Wallets withdrawFromWallet(int id, double amount) {
        Wallets wallet = walletRepo.findByUser_UserId(id).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + id));
        if (wallet.getBalance() < amount) {
            throw new BalanceNotEnoughException("Balance in Wallet for UserId: "+id+" is not enough");// Insufficient funds
        }
        WalletTransaction walletTransaction = new WalletTransaction(TransactionType.WITHDRAWAL,amount, LocalDateTime.now(),wallet);
        transactionRepo.save(walletTransaction);
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepo.save(wallet);
    }
    public Boolean checkBalance(int userid, double amount){
        Wallets wallet = walletRepo.findByUser_UserId(userid).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + userid));
        return wallet.getBalance() >= amount;
    }
    public Wallets transferBetweenWallets(int fromUserId,int toUserId,double amount){
        Wallets fromWallet = walletRepo.findByUser_UserId(fromUserId).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + fromUserId));
        Wallets toWallet = walletRepo.findByUser_UserId(toUserId).orElseThrow(()->new WalletNotFoundException("Wallet not found for user ID: " + toUserId));
        if (fromWallet.getBalance() < amount) {
            throw new BalanceNotEnoughException("Balance in Wallet for UserId: "+fromUserId+" is not enough");// Insufficient funds
        }
        WalletTransaction fromTransaction = new WalletTransaction(TransactionType.TRANSFER,amount, LocalDateTime.now(),fromWallet);
        transactionRepo.save(fromTransaction);
        WalletTransaction toTransaction = new WalletTransaction(TransactionType.TRANSFER,amount, LocalDateTime.now(),toWallet);
        transactionRepo.save(toTransaction);
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        walletRepo.save(fromWallet);
        return walletRepo.save(toWallet);
    }

}
