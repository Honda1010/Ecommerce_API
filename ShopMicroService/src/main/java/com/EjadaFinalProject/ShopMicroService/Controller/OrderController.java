package com.EjadaFinalProject.ShopMicroService.Controller;

import com.EjadaFinalProject.ShopMicroService.Model.Order.Order;
import com.EjadaFinalProject.ShopMicroService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/PlaceOrder/{userid}")
    ResponseEntity<Order> PlaceOrder(@PathVariable int userid){
        Order order=orderService.PlaceOrder(userid);
        return ResponseEntity.ok(order);
    }
    @PostMapping("/CancelOrder/{orderid}")
    ResponseEntity<Void> CancelOrder(@PathVariable int orderid){
        orderService.CancelOrder(orderid);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/GetOrder/{orderid}")
    ResponseEntity<Order> GetOrderById(@PathVariable int orderid){
        Order order=orderService.GetOrder(orderid);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/GetUserOrders/{userid}")
    ResponseEntity<List<Order>> GetUserOrders(@PathVariable int userid){
        List<Order> orders=orderService.GetAllOrders(userid);
        return ResponseEntity.ok(orders);
    }

}
