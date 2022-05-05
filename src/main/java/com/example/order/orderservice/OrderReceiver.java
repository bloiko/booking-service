package com.example.order.orderservice;

import com.example.order.pojos.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderReceiver {

    @Autowired
    OrderRepository orderRepository;

    @JmsListener(destination = "order")
    public void receiveLiquidsMessage(Order order) {
        String message = String.format("Order %s with was taken into handling", order);
        System.out.println(message);

        orderRepository.save(order);
    }
}
