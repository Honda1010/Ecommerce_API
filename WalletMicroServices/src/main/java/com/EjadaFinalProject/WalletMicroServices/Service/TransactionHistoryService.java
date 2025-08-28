package com.EjadaFinalProject.WalletMicroServices.Service;

import com.EjadaFinalProject.WalletMicroServices.Exceptions.WalletNotFoundException;
import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import com.EjadaFinalProject.WalletMicroServices.Repo.TransactionRepo;
import com.EjadaFinalProject.WalletMicroServices.Repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryService {
    @Autowired
    WalletRepo walletRepo;
    @Autowired
    TransactionRepo transactionRepo;
    public List<WalletTransaction> GetTransactionHistory(int userId) {
        Wallets wallet = walletRepo.findByUser_UserId(userId).orElseThrow(()-> new WalletNotFoundException("Wallet not found for user ID: " + userId));
        List<WalletTransaction> transactions = transactionRepo.findByWallet_WalletId(wallet.getWalletId());
        return transactions;
    }
}
