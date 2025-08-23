package com.EjadaFinalProject.ShopMicroService.Proxy;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventorymicroservice")
public interface InventoryProxy {
    @GetMapping("/inventory/products/{id}")
    InventoryProductDto GetProductById(@PathVariable int id);
    @PostMapping("/inventory/products/reduce/{id}/{quantity}")
    void reduceProduct(@PathVariable int id, @PathVariable int quantity);
    @PostMapping(path = "/inventory/products/release/{id}/{quantity}")
    void releaseproduct(@PathVariable int id, @PathVariable int quantity);
}
