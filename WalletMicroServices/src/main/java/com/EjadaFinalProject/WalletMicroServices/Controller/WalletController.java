package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/wallets")
@RestController
public class WalletController {
    @Autowired
    private WalletRepo walletRepo;
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
            return ResponseEntity.notFound().build();
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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/byid/{walletId}")
    ResponseEntity<Wallets> getWalletById(@PathVariable int walletId) {
        Wallets wallet = walletRepo.findById(walletId).orElse(null);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteWallet(@PathVariable int userId) {
        Wallets wallet = walletRepo.findByUserId(userId);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
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
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        wallet.setBalance(wallet.getBalance() + amount);
        Wallets updatedWallet = walletRepo.save(wallet);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/withdraw/{userId}/{amount}")
    ResponseEntity<Wallets> withdrawFromWallet(@PathVariable int userId, @PathVariable double amount) {
        Wallets wallet = walletRepo.findByUserId(userId);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        if (wallet.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Insufficient funds
        }
        wallet.setBalance(wallet.getBalance() - amount);
        Wallets updatedWallet = walletRepo.save(wallet);
        return ResponseEntity.ok(updatedWallet);
    }

}
