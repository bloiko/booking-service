package com.example.order.orderservice;

import com.example.order.pojos.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    public Order send(Order order) {
//        order.setId(0L);
//        order.getTicket().setId(0L);
        return orderProducer.send(order);
    }

    public Iterable<Order> getAllOrders() {
        Iterable<Order> list = orderRepository.findAll();
        List<Order> result =
                StreamSupport.stream(list.spliterator(), false)
                        .collect(Collectors.toList());
        return result;
    }
}
