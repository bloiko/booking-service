package com.example.order;

import com.example.DbConfiguration;
import com.example.JmsConfig;
import com.example.TicketApplication;
import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.TestHelper.buildOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")

@ContextConfiguration(
        classes = { DbConfiguration.class, JmsConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest(classes = TicketApplication.class)
public class OrderControllerIT {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    OrderController orderController;


    @Transactional
    @Rollback
    @Test
    public void testRetrieveOrdersSync() throws Exception {
        Date date = new Date(2022, 20, 20);
        Order order = buildOrder(date, 1L, "source1", "destination1", "testUsername1");

        orderController.orderBook(order);

        Thread.sleep(100);

        List<Order> all = orderRepository.findAll();
        assertTrue(all.stream()
                .anyMatch(orderTemp -> areOrdersEqual(orderTemp, order)));
    }


    @Transactional
    @Rollback
    @Test
    public void testRetrieveOrdersAsync() throws Exception {
        Date date = new Date(2022, 20, 20);
        Order order1 = buildOrder(date, 0, "source", "destination", "testUsername");
        Order order2 = buildOrder(date, 0, "source2", "destination2", "testUsername2");
        Order order3 = buildOrder(date, 0, "source3", "destination3", "testUsername3");
        List<Order> orders = Lists.newArrayList(order1, order2, order3);

        orders.parallelStream()
                .map(order -> orderController.orderBook(order))
                .collect(Collectors.toList());

        Thread.sleep(10000);

        List<Order> all = orderRepository.findAll();
        assertEquals(all.size(), 3);
    }

    private boolean areOrdersEqual(Order orderTemp, Order order) {
        Ticket ticketTemp = orderTemp.getTicket();
        Ticket ticket = order.getTicket();

        return orderTemp.getUsername().equals(order.getUsername()) &&
                ticketTemp.getSource().equals(ticket.getSource()) &&
                ticketTemp.getDestination().equals(ticket.getDestination()) &&
                ticketTemp.getPrice().equals(ticket.getPrice());
    }
}
