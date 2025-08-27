package com.EjadaFinalProject.ShopMicroService.Repo;

import com.EjadaFinalProject.ShopMicroService.Model.Product.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ShopProduct, Integer> {
    public ShopProduct findByInventoryProductId(Integer inventoryProductId);
}
