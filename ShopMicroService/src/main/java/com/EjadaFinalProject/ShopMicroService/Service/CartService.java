package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import com.EjadaFinalProject.ShopMicroService.Exceptions.CartExceptions.CartNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.InventoryProductNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.InventoryServiceIsNotAvailableException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.ProductNotInStockException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.ProductQuntityIsNotEnoughInStockException;
import com.EjadaFinalProject.ShopMicroService.Model.Cart.Cart;
import com.EjadaFinalProject.ShopMicroService.Model.Cart.CartItem;
import com.EjadaFinalProject.ShopMicroService.Model.Product.ShopProduct;
import com.EjadaFinalProject.ShopMicroService.Proxy.InventoryProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.CartItemRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.CartRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.ProductRepo;
import com.EjadaFinalProject.ShopMicroService.Wrappers.InventoryWrapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private InventoryWrapper inventoryWrapper;

    public Cart CreateCart(int userId){
        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepo.save(cart);
    }
    public CartItem addtoCart(int userId, int productId, int quantity){
        Cart cart = cartRepo.findByUserId(userId);
        if (cart == null) {
            cart = CreateCart(userId);
        }
        ShopProduct product = productRepo.findById(productId).get();
        InventoryProductDto inventoryProduct = inventoryWrapper.GetProductById(product.getInventoryProductId());
        // Check if the product exists in inventory and has enough quantity
        if (inventoryProduct.getProductName().equals("N/A") && inventoryProduct.getProductDescription().equals("Inventory service unavailable")) {
            throw new InventoryServiceIsNotAvailableException("Inventory service unavailable Try again Later");
        }
        if (inventoryProduct == null) throw new InventoryProductNotFoundException("Product not found in inventory with id: " + product.getInventoryProductId());
        if (inventoryProduct.getProductQuantity() < quantity) throw new ProductQuntityIsNotEnoughInStockException("Product quantity less than quantity to add to cart for product id: " + productId);
        if (inventoryProduct.getProductQuantity() <= 0) throw new ProductNotInStockException("this product is out of stock for product id: " + productId);
        // Check if the product is already in the cart
        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(product.getPrice() * item.getQuantity());
                cart.CalculateTotalPrice();
                cartRepo.save(cart);
                return item;
            }
        }
        // If the product is not in the cart, add it as a new item
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice()*quantity);
        cart.getCartItems().add(cartItem);
        cart.CalculateTotalPrice();
        cartRepo.save(cart);

        return cartItem;
    }
    @Transactional
    public void RemoveFromCart(int userId, int productId){
        Cart cart = cartRepo.findByUserId(userId);
        if (cart != null) {
            cart.getCartItems().removeIf(item -> item.getProductId() == productId);
            cartItemRepo.deleteByProductId(productId);
            cart.CalculateTotalPrice();
            cartRepo.save(cart);
        }
        else  {
            throw new CartNotFoundException("Cart not found for user id: " + userId);
        }
    }
    @Transactional
    public void clearCart(int userId) {
        Cart cart = cartRepo.findByUserId(userId);
        if (cart != null) {
            for ( CartItem item : cart.getCartItems()) {
                cartItemRepo.deleteByProductIdAndCartId(item.getProductId(),cart.getId());
            }
            cart.getCartItems().clear();
            cart.setTotalPrice(0.0);
            cartRepo.save(cart);
        } else {
            throw new CartNotFoundException("Cart not found for user id: " + userId);
        }
    }
    public List<CartItem> getCartItems(int userId){
        Cart cart = cartRepo.findByUserId(userId);
        if (cart != null) {
            return cart.getCartItems();
        } else {
            throw new CartNotFoundException("Cart not found for user id: " + userId);
        }
    }
    public Cart getCartByUserId(int userId){
        Cart cart = cartRepo.findByUserId(userId);
        if (cart != null) {
            return cart;
        } else {
            throw new CartNotFoundException("Cart not found for user id: " + userId);
        }
    }

}
