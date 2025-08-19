package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepo extends CrudRepository<WalletTransaction, Integer> {
    WalletTransaction findByTransactionId(Integer transactionId);
    List<WalletTransaction> findByWallet_WalletId(Integer walletId);
}
