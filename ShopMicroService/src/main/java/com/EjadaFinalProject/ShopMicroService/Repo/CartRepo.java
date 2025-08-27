package com.EjadaFinalProject.ShopMicroService.Repo;

import com.EjadaFinalProject.ShopMicroService.Model.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Integer> {
    Cart findByUserId(int userId);
    void deleteByUserId(int userId);
}
