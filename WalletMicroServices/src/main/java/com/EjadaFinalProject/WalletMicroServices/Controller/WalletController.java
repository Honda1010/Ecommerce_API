package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.BalanceNotEnoughException;
import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.UserRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import com.EjadaFinalProject.WalletMicroServices.Service.UserService;
import com.EjadaFinalProject.WalletMicroServices.Service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/wallets")
@RestController
public class WalletController {
    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private WalletService walletService;

    @PostMapping("/create/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Wallets> createWalletForUser(@PathVariable Integer userId) {
        Wallets savedWallet = walletService.CreateWallet(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWallet);
    }
    @PostMapping("/update/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Wallets> updateWalletForUser(@PathVariable int userId, @RequestBody Wallets wallet) {
        Wallets updatedWallet = walletService.UpdateWallet(userId, wallet);
        return ResponseEntity.ok(updatedWallet);
    }

    @GetMapping("/byuser/{userId}")
    ResponseEntity<Wallets> getWalletByUserId(@PathVariable int userId) {
        Wallets wallet = walletService.GetWalletByUserId(userId);
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
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteWalletForUser(@PathVariable int userId) {
        walletService.DeleteWallet(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<Wallets>> getAllWallets() {
        List<Wallets> wallets = walletRepo.findAll();
        return ResponseEntity.ok(wallets);
    }
    @PostMapping("/deposit/{userId}/{amount}")
    ResponseEntity<Wallets> depositToWalletForUser(@PathVariable int userId, @PathVariable double amount) {
        Wallets updatedWallet = walletService.depositToWallet(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/withdraw/{userId}/{amount}")
    ResponseEntity<Wallets> withdrawFromWalletForUser(@PathVariable int userId, @PathVariable double amount) {
        Wallets updatedWallet = walletService.withdrawFromWallet(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/transfer/{fromUserId}/{toUserId}/{amount}")
    ResponseEntity<Wallets> transferBetweenWalletsForUser(@PathVariable int fromUserId,
                                                   @PathVariable int toUserId,
                                                   @PathVariable double amount) {
        Wallets updatedToWallet = walletService.transferBetweenWallets(fromUserId, toUserId, amount);
        return ResponseEntity.ok(updatedToWallet);
    }
    @GetMapping("/checkbalance/{userId}/{amount}")
    ResponseEntity<Boolean> checkBalanceForUser(@PathVariable int userId, @PathVariable double amount) {
        boolean hasEnoughBalance = walletService.checkBalance(userId, amount);
        return ResponseEntity.ok(hasEnoughBalance);
    }

}
