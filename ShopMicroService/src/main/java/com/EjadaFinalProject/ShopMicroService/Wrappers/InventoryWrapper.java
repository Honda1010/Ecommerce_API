package com.EjadaFinalProject.ShopMicroService.Wrappers;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import com.EjadaFinalProject.ShopMicroService.Proxy.InventoryProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryWrapper {
    @Autowired
    private InventoryProxy inventoryProxy;
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackGetInventory")
    public InventoryProductDto GetProductById(int id){
        return inventoryProxy.GetProductById(id);
    }
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackGetInventoryForReduce")
    public String reduceProduct(int id , int quantity){
        inventoryProxy.reduceProduct(id,quantity);
        return "success";
    }
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackGetInventoryForRelease")
    public String releaseproduct(int id , int quantity) {
        inventoryProxy.releaseproduct(id, quantity);
        return "success";
    }
    // Fallback method
    public InventoryProductDto fallbackGetInventory(int id , Throwable e){
        InventoryProductDto fallbackProduct = new InventoryProductDto();
        fallbackProduct.setProductName("N/A");
        fallbackProduct.setProductDescription("Inventory service unavailable");
        fallbackProduct.setProductQuantity(0);
        return fallbackProduct;
    }
    public String fallbackGetInventoryForReduce(int id, int quantity, Throwable e) {
        return "Failed";
    }
    public String fallbackGetInventoryForRelease(int id, int quantity, Throwable e) {
        return "Failed";
    }


}
