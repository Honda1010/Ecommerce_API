package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepo extends JpaRepository<Wallets, Integer> {
    Optional<Wallets> findByUserId(Integer userId);
}
