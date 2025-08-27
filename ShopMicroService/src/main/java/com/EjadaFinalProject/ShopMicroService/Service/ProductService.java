package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.InventoryProductNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.ProductAlreadyExistInShopException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.ProductNotInStockException;
import com.EjadaFinalProject.ShopMicroService.Model.ShopProduct;
import com.EjadaFinalProject.ShopMicroService.Proxy.InventoryProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    InventoryProxy inventoryProxy;

    public ShopProduct addProductFromInventory(int id) {
        ShopProduct FoundedProduct = productRepo.findByInventoryProductId(id);
        if (FoundedProduct != null) {
            throw new ProductAlreadyExistInShopException("This Product with inventory id : "+id+" is Already Exist in Shop");
        }
        InventoryProductDto inventoryproduct = inventoryProxy.GetProductById(id);

        if (inventoryproduct == null) {
            throw new InventoryProductNotFoundException("This Product with : "+id+" is Not Found in Inventory");

        }
        if (inventoryproduct.getProductQuantity() == 0) {
            throw new ProductNotInStockException("This Product with : "+id+" is Not In Stock");
        }
        ShopProduct product = new ShopProduct();
        product.setInventoryProductId(inventoryproduct.getProductId());
        product.setDescription(inventoryproduct.getProductDescription());
        product.setPrice(inventoryproduct.getProductPrice());
        product.setName(inventoryproduct.getProductName());
        return productRepo.save(product);
    }
    public ShopProduct getProductById(int id) {
        return productRepo.findById(id).get();
    }
    public List<ShopProduct> getAllProducts() {
        return productRepo.findAll();
    }
}
