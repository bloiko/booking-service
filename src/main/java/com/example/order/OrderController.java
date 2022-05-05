package com.example.order;

import com.example.order.orderservice.OrderService;
import com.example.order.pojos.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public Order orderBook(@RequestBody Order order) {
        return orderService.send(order);
    }

    @GetMapping("/orders")
    public Iterable<Order> getBooks() {
        return orderService.getAllOrders();
    }
}
