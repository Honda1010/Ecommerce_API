package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import com.EjadaFinalProject.ShopMicroService.Exceptions.CartEmptyException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.CartNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderIsAlreadyCancelledException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductQuntityIsNotEnoughInStockException;
import com.EjadaFinalProject.ShopMicroService.Model.*;
import com.EjadaFinalProject.ShopMicroService.Proxy.InventoryProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.CartRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.OrderRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private InventoryProxy inventoryProxy;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartService cartService;

    private Order CreateOrder(int userId){
        Order order = new Order();
        order.setUserId(userId);
        return orderRepo.save(order);
    }
    public Order PlaceOrder(int userId){
        Cart cart = cartRepo.findByUserId(userId);
        Order order = CreateOrder(userId);
        if (cart == null){
            throw new CartNotFoundException("Cart not found for user id: " + userId);
        }
        if (cart.getCartItems()==null || cart.getCartItems().isEmpty()){
            throw new CartEmptyException("Cart is empty for user id: " + userId);
        }
        for (CartItem cartItem : cart.getCartItems()){
            ShopProduct product = productRepo.findById(cartItem.getProductId()).orElse(null);
            if (product == null){
                throw new CartNotFoundException("Product not found for cart id: " + cartItem.getProductId());
            }
            InventoryProductDto inventoryProductDto = inventoryProxy.GetProductById(product.getInventoryProductId());
            if (inventoryProductDto.getProductQuantity() < cartItem.getQuantity()){
                throw new ProductQuntityIsNotEnoughInStockException("Product quantity less than quantity to add to Your Order for product id: " + product.getInventoryProductId());
            }
            inventoryProxy.reduceProduct(product.getInventoryProductId(), cartItem.getQuantity());
            OrderItem orderItem = new OrderItem(cartItem.getProductId(),cartItem.getQuantity(),cartItem.getPrice(),order);
            order.getOrderItems().add(orderItem);

        }
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setCartId(cart.getId());
        orderRepo.save(order);
        cartService.clearCart(userId);
        return order;
    }
    public void CancelOrder(int orderId){
        Order order = orderRepo.findById(orderId).get();
        if (order == null){
            throw new CartNotFoundException("Order not found for order id: " + orderId);
        }
        if (order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new OrderIsAlreadyCancelledException("Order is already cancelled");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        for (OrderItem orderItem : order.getOrderItems()){
            ShopProduct product = productRepo.findById(orderItem.getProductId()).get();
            inventoryProxy.releaseproduct(product.getInventoryProductId(), orderItem.getQuantity());
        }
        orderRepo.save(order);
    }
    public Order GetOrder(int orderId){
        return orderRepo.findById(orderId).get();
    }
    public List<Order> GetAllOrders(int userid){
        return orderRepo.findByUserId(userid);
    }
}
