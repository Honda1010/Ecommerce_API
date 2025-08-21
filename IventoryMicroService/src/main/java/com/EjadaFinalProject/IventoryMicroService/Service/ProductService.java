package com.EjadaFinalProject.IventoryMicroService.Service;

import com.EjadaFinalProject.IventoryMicroService.Exception.ProductNotFoundException;
import com.EjadaFinalProject.IventoryMicroService.Exception.QuantityNotEnoughException;
import com.EjadaFinalProject.IventoryMicroService.Model.InventoryProduct;
import com.EjadaFinalProject.IventoryMicroService.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    public InventoryProduct findByProductId(int productId) {
        InventoryProduct FoundedProduct = productRepo.findById(productId).orElse(null);
        if (FoundedProduct == null) {
            throw new ProductNotFoundException("This Product with Id: "+productId+" does not exist in the inventory");
        }
        return FoundedProduct;
    }
    public List<InventoryProduct> GetAllProduct(){
        return productRepo.findAll();
    }
    public void  DeleteProductById(int productId) {
        productRepo.deleteById(productId);
    }
    public InventoryProduct AddProduct(InventoryProduct product) {
        return productRepo.save(product);
    }
    public InventoryProduct UpdateProduct(int id,InventoryProduct product) {
        InventoryProduct FoundedProduct = productRepo.findById(id).orElse(null);
        if (FoundedProduct == null) {
            throw new ProductNotFoundException("This Product with Id: "+id+" does not exist in the inventory");
        }
        FoundedProduct = new InventoryProduct(product.getProductDescription(),id,product.getProductName(),product.getProductPrice(),product.getProductQuantity());
        return productRepo.save(FoundedProduct);
    }
    public InventoryProduct ReduceStock(int productId,int quantity) {
        return productRepo.findById(productId).map(p->{
            if(p.getProductQuantity()>quantity){
                p.setProductQuantity(p.getProductQuantity()-quantity);
                return productRepo.save(p);
            }
            else {
                throw new QuantityNotEnoughException("This Product with Id: "+productId+" does not have enough stock to reduce by "+quantity);
            }
        }).orElseThrow(()->new ProductNotFoundException("This Product with Id: "+productId+" does not exist in the inventory"));
    }
    public  InventoryProduct RelaseStock(int productId,int quantity) {
        return productRepo.findById(productId).map(p->{
            p.setProductQuantity(p.getProductQuantity()+quantity);
            return productRepo.save(p);
        }).orElseThrow(()->new ProductNotFoundException("This Product with Id: "+productId+" does not exist in the inventory"));
    }
}
