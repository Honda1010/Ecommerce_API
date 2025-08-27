package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.WalletTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends CrudRepository<WalletTransaction, Integer> {
    Optional<WalletTransaction> findByTransactionId(Integer transactionId);
    List<WalletTransaction> findByWallet_WalletId(Integer walletId);
}
