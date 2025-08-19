package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.BalanceNotEnoughException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/wallets")
@RestController
public class WalletController {
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @PostMapping("/create/{userId}")
    ResponseEntity<Wallets> createWalletForUser(@PathVariable Integer userId) {
        Wallets existingWallet = walletRepo.findByUserId(userId);
        if (existingWallet != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Wallet already exists for this user
        }
        // Create a new wallet for the user
        Wallets wallet = new Wallets();
        wallet.setUserId(userId);
        Wallets savedWallet = walletRepo.save(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWallet);
    }
    @PostMapping("/update/{userId}")
    ResponseEntity<Wallets> updateWallet(@PathVariable int userId, @RequestBody Wallets wallet) {
        Wallets existingWallet = walletRepo.findByUserId(userId);
        if (existingWallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        existingWallet.setBalance(wallet.getBalance());
        existingWallet.setUserId(wallet.getUserId());
        Wallets updatedWallet = walletRepo.save(existingWallet);
        return ResponseEntity.ok(updatedWallet);
    }

    @GetMapping("/byuser/{userId}")
    ResponseEntity<Wallets> getWalletByUserId(@PathVariable int userId) {
        Wallets wallet = walletRepo.findByUserId(userId);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/byid/{walletId}")
    ResponseEntity<Wallets> getWalletById(@PathVariable int walletId) {
        Wallets wallet = walletRepo.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for wallet ID: " + walletId);
        }
        return ResponseEntity.ok(wallet);
    }
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteWallet(@PathVariable int userId) {
        Wallets wallet = walletRepo.findByUserId(userId);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        walletRepo.delete(wallet);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    ResponseEntity<List<Wallets>> getAllWallets() {
        List<Wallets> wallets = walletRepo.findAll();
        return ResponseEntity.ok(wallets);
    }
    @PostMapping("/deposit/{userId}/{amount}")
    ResponseEntity<Wallets> depositToWallet(@PathVariable int userId, @PathVariable double amount) {
        Wallets wallet = walletRepo.findByUserId(userId);
        WalletTransaction walletTransaction = new WalletTransaction();
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        // Create a new transaction record
        walletTransaction.setWallet(wallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setType("CREDIT");
        walletTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(walletTransaction);
        // Update the wallet balance
        wallet.setBalance(wallet.getBalance() + amount);
        Wallets updatedWallet = walletRepo.save(wallet);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/withdraw/{userId}/{amount}")
    ResponseEntity<Wallets> withdrawFromWallet(@PathVariable int userId, @PathVariable double amount) {
        Wallets wallet = walletRepo.findByUserId(userId);
        WalletTransaction walletTransaction = new WalletTransaction();
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        if (wallet.getBalance() < amount) {
            throw new BalanceNotEnoughException("Balance in Wallet for UserId: "+userId+" is not enough");// Insufficient funds
        }
        // Create a new transaction record
        walletTransaction.setWallet(wallet);
        walletTransaction.setAmount(amount);
        walletTransaction.setType("DEBIT");
        walletTransaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(walletTransaction);
        wallet.setBalance(wallet.getBalance() - amount);
        Wallets updatedWallet = walletRepo.save(wallet);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/transfer/{fromUserId}/{toUserId}/{amount}")
    ResponseEntity<Wallets> transferBetweenWallets(@PathVariable int fromUserId,
                                                   @PathVariable int toUserId,
                                                   @PathVariable double amount) {
        Wallets fromWallet = walletRepo.findByUserId(fromUserId);
        Wallets toWallet = walletRepo.findByUserId(toUserId);
        WalletTransaction fromTransaction = new WalletTransaction();
        WalletTransaction toTransaction = new WalletTransaction();
        if (fromWallet == null || toWallet == null) {
            return ResponseEntity.notFound().build();
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
        Wallets updatedToWallet = walletRepo.save(toWallet);
        return ResponseEntity.ok(updatedToWallet);
    }

}
