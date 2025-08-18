package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallets, Integer> {
    Wallets findByUserId(Integer userId);
}
