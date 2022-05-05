package com.example.order.orderservice;

import com.example.order.pojos.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderProducer {
    private static final String ORDER_QUEUE = "order";

    @Autowired
    @Qualifier("jmsTemplateCustom")
    private JmsTemplate jmsTemplate;

    public Order send(Order order){
        jmsTemplate.convertAndSend(ORDER_QUEUE, order);
        return order;
    }

}
