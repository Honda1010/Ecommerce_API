package com.EjadaFinalProject.ShopMicroService.Model;

import jakarta.persistence.*;

@Entity
public class ShopProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(name = "inventoryProductId")
    private Integer inventoryProductId;
    private String name;
    private String description;
    private Double price;

    public ShopProduct() {
    }

    public ShopProduct(Integer id,Integer InventoryProductId, String name, String description, Integer quantity, Double price) {
        this.productId = id;
        this.inventoryProductId = InventoryProductId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return productId;
    }

    public void setId(Integer id) {
        this.productId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getInventoryProductId() {
        return inventoryProductId;
    }
    public void setInventoryProductId(Integer inventoryProductId) {
        this.inventoryProductId = inventoryProductId;
    }

}
