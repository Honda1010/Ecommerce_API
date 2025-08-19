package com.EjadaFinalProject.WalletMicroServices.Controller;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
    @Autowired
    WalletRepo walletRepo;
    @Autowired
    TransactionRepo transactionRepo;
    @GetMapping("/{userId}")
    public ResponseEntity<List<WalletTransaction>> TransactionHistory(@PathVariable int userId) {
        Wallets wallet = walletRepo.findByUserId(userId);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user ID: " + userId);
        }
        List<WalletTransaction> transactions = transactionRepo.findByWallet_WalletId(wallet.getWalletId());
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build(); // No transactions found
        }
        return ResponseEntity.ok(transactions);
    }

}
