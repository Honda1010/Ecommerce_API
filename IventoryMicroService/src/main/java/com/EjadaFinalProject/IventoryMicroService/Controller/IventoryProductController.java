package com.EjadaFinalProject.IventoryMicroService.Controller;

import com.EjadaFinalProject.IventoryMicroService.Model.InventoryProduct;
import com.EjadaFinalProject.IventoryMicroService.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/inventory")
@RestController
public class IventoryProductController {
    @Autowired
    private ProductService productService;
    @GetMapping(path="/products/{id}")
    ResponseEntity<InventoryProduct> GetProductById(@PathVariable int id)
    {
        InventoryProduct foundedproduct= productService.findByProductId(id);

        return ResponseEntity.ok(foundedproduct);
    }
    @GetMapping(path = "/products")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<InventoryProduct>> GetAllProducts()
    {
        List<InventoryProduct> products = productService.GetAllProduct();
        return ResponseEntity.ok(products);
    }
    @PostMapping(path = "/products/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<InventoryProduct> addProduct(@Valid @RequestBody InventoryProduct product)
    {
        InventoryProduct NewProduct= productService.AddProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(NewProduct);
    }
    @DeleteMapping(path = "/products/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void  deleteProduct(@PathVariable int id)
    {
        productService.DeleteProductById(id);
    }
    @PostMapping(path = "/products/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<InventoryProduct>  updateProduct(@Valid @RequestBody InventoryProduct product, @PathVariable int id)
    {
        InventoryProduct UpdatedProduct= productService.UpdateProduct(id, product);
        return ResponseEntity.ok(UpdatedProduct);
    }
    @PostMapping(path = "/products/reduce/{id}/{quantity}")
    ResponseEntity<InventoryProduct> reduceProduct(@PathVariable int id, @PathVariable int quantity)
    {
        InventoryProduct ReducedProduct = productService.ReduceStock(id, quantity);
        return ResponseEntity.ok(ReducedProduct);
    }
    @PostMapping(path = "/products/release/{id}/{quantity}")
    ResponseEntity<InventoryProduct> releaseproduct(@PathVariable int id, @PathVariable int quantity)
    {
        InventoryProduct ReleasedProduct = productService.RelaseStock(id, quantity);
        return ResponseEntity.ok(ReleasedProduct);
    }


}
