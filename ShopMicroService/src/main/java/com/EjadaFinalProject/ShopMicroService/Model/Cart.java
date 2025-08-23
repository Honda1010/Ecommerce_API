package com.EjadaFinalProject.ShopMicroService.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer Id;
    private Integer userId;
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    private Double totalPrice;
    public Cart() {

    }
    public Cart(Integer id, Integer userId, List<CartItem> cartItems, Double totalPrice) {
        Id = id;
        this.userId = userId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void addToTotalPrice(int price){
        this.totalPrice += price;
    }
    public void removeFromTotalPrice(int price){
        this.totalPrice -= price;
    }
    public void CalculateTotalPrice(){
        this.totalPrice = 0.0;
        List<CartItem> cartItems = getCartItems();
        for(CartItem item : cartItems){
            this.totalPrice += item.getPrice();
        }
    }

}
