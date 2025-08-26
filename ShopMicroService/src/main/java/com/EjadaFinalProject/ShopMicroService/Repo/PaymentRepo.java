package com.EjadaFinalProject.ShopMicroService.Repo;

import com.EjadaFinalProject.ShopMicroService.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
    List<Payment> findByUserId(int userId);
}
