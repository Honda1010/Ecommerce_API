package com.EjadaFinalProject.ShopMicroService.Repo;

import com.EjadaFinalProject.ShopMicroService.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    void deleteByProductId(int productId);
}
