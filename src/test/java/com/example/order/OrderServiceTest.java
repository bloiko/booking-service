package com.example.order;

import com.example.order.orderservice.OrderProducer;
import com.example.order.orderservice.OrderService;
import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.example.TestHelper.buildOrder;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderProducer orderProducer;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testSend() {
        Date date = new Date(2022, 20, 20);
        Order order = buildOrder(date, 1L, "source", "destination", "testUsername");
        given(orderProducer.send(order)).willReturn(order);

        Order sendedOrder = orderService.send(order);

        then(sendedOrder)
                .as("Check if sended order is the same as initial.")
                .isEqualTo(order);
        Mockito.verify(orderProducer, times(1)).send(order);
    }

    @Test
    public void testGetAllOrders() {
        Date date = new Date(2022, 20, 20);
        Order order = buildOrder(date, 1L, "source", "destination", "testUsername");
        Order order2 = buildOrder(date, 2L, "source2", "destination2", "testUsername2");

        given(orderRepository.findAll()).willReturn(Lists.newArrayList(order, order2));

        Iterable<Order> orders = orderService.getAllOrders();

        then(orders)
                .hasSize(2);

        Iterator<Order> orderIterator = orders.iterator();
        then(orderIterator.next())
                .isEqualTo(order);
        then(orderIterator.next())
                .isEqualTo(order2);
        Mockito.verify(orderRepository, times(1)).findAll();
    }
}
