package com.EjadaFinalProject.ShopMicroService.Controller;

import com.EjadaFinalProject.ShopMicroService.Model.Cart;
import com.EjadaFinalProject.ShopMicroService.Model.CartItem;
import com.EjadaFinalProject.ShopMicroService.Service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable int productId ,@PathVariable int quantity,@PathVariable int userId) {
        CartItem cartitem= cartService.addtoCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/remove/{userId}/{ProductId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable int userId,@PathVariable int ProductId) {
        cartService.RemoveFromCart(userId, ProductId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable int userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/cartitems/{userId}")
    public ResponseEntity<List<CartItem>> viewCart(@PathVariable int userId) {
        List<CartItem> cartitem= cartService.getCartItems(userId);
        return ResponseEntity.ok(cartitem);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId) {
        Cart cart= cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

}
