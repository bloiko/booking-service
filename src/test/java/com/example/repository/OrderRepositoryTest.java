package com.example.repository;

import com.example.DbConfiguration;
import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void givenStudent_whenSave_thenGetOk() {
        Date date = new Date(2022, 20, 20);
        Ticket ticket = new Ticket(1L, "source", "destination", date, 1000L);
        Order order = new Order(1L, "testUsername", ticket);
        orderRepository.save(order);

        List<Order> all = orderRepository.findAll();
        Order order2 = all.get(0);
        Ticket ticket2 = order2.getTicket();
        assertEquals("testUsername", order2.getUsername());
        assertEquals("source", ticket2.getSource());
        assertEquals("destination", ticket2.getDestination());
        assertEquals(date, ticket2.getDate());
        assertEquals(ticket.getPrice(), ticket2.getPrice());
    }
}
