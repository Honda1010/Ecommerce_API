package com.EjadaFinalProject.ShopMicroService.Controller;

import com.EjadaFinalProject.ShopMicroService.Model.Product.ShopProduct;
import com.EjadaFinalProject.ShopMicroService.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/add/{InventoryId}" )
    public ResponseEntity<ShopProduct> AddProductFromInventory(@PathVariable int InventoryId){
       ShopProduct product= productService.addProductFromInventory(InventoryId);
       return ResponseEntity.ok(product);
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<ShopProduct>> getAllProducts(){
        List<ShopProduct> products= productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping(path = "/{productId}" )
    public ResponseEntity<ShopProduct> getProductById(@PathVariable int productId){
        ShopProduct product= productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}
