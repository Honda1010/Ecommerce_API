package com.EjadaFinalProject.WalletMicroServices.Repo;

import com.EjadaFinalProject.WalletMicroServices.Model.Wallet_SnapShot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapShotRepo extends JpaRepository<Wallet_SnapShot, Integer> {
}
