package com.EjadaFinalProject.IventoryMicroService.Repo;

import com.EjadaFinalProject.IventoryMicroService.Model.InventoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<InventoryProduct, Integer> {
}
