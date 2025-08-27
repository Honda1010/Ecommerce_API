package com.EjadaFinalProject.ShopMicroService.Service;

import com.EjadaFinalProject.ShopMicroService.Dto.InventoryProductDto;
import com.EjadaFinalProject.ShopMicroService.Exceptions.CartExceptions.CartEmptyException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.CartExceptions.CartNotFoundException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.OrderExceptions.OrderIsAlreadyCancelledException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.InventoryServiceIsNotAvailableException;
import com.EjadaFinalProject.ShopMicroService.Exceptions.ProductsExceptions.ProductQuntityIsNotEnoughInStockException;
import com.EjadaFinalProject.ShopMicroService.Model.Cart.Cart;
import com.EjadaFinalProject.ShopMicroService.Model.Cart.CartItem;
import com.EjadaFinalProject.ShopMicroService.Model.Order.Order;
import com.EjadaFinalProject.ShopMicroService.Model.Order.OrderItem;
import com.EjadaFinalProject.ShopMicroService.Model.Order.OrderStatus;
import com.EjadaFinalProject.ShopMicroService.Model.Product.ShopProduct;
import com.EjadaFinalProject.ShopMicroService.Proxy.InventoryProxy;
import com.EjadaFinalProject.ShopMicroService.Repo.CartRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.OrderRepo;
import com.EjadaFinalProject.ShopMicroService.Repo.ProductRepo;
import com.EjadaFinalProject.ShopMicroService.Wrappers.InventoryWrapper;
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
    private ProductRepo productRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private InventoryWrapper inventoryWrapper;


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
            InventoryProductDto inventoryProductDto = inventoryWrapper.GetProductById(product.getInventoryProductId());
            if (inventoryProductDto.getProductName().equals("N/A") && inventoryProductDto.getProductDescription().equals("Inventory service unavailable")) {
                throw new InventoryServiceIsNotAvailableException("Inventory service unavailable Try again Later");
            }
            if (inventoryProductDto.getProductQuantity() < cartItem.getQuantity()){
                throw new ProductQuntityIsNotEnoughInStockException("Product quantity less than quantity to add to Your Order for product id: " + product.getInventoryProductId());
            }
            String msg= inventoryWrapper.reduceProduct(product.getInventoryProductId(), cartItem.getQuantity());
            if(!msg.equals("success")){
                throw new InventoryServiceIsNotAvailableException("Inventory service unavailable Try again Later");
            }
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
            String msg= inventoryWrapper.releaseproduct(product.getInventoryProductId(), orderItem.getQuantity());
            if(!msg.equals("success")){
                throw new InventoryServiceIsNotAvailableException("Inventory service unavailable Try again Later");
            }

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
