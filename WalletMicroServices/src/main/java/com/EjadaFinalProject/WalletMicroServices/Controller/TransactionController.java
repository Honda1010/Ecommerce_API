package com.EjadaFinalProject.WalletMicroServices.Controller;


import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Service.TransactionHistoryService;
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
    TransactionHistoryService transactionHistoryService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<WalletTransaction>> TransactionHistory(@PathVariable int userId) {
        List<WalletTransaction> transactions = transactionHistoryService.GetTransactionHistory(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build(); // No transactions found
        }
        return ResponseEntity.ok(transactions);
    }

}
