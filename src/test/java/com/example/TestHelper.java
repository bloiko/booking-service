package com.example;

import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;

import java.util.Date;

public class TestHelper {
    public static Order buildOrder(Date date, long l, String source, String destination, String testUsername) {
        Ticket ticket = new Ticket(l, source, destination, date, 1000L);
        return new Order(l, testUsername, ticket);
    }
}
