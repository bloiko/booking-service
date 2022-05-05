package com.example.order;

import static com.sun.javafx.fxml.expression.Expression.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;
import java.util.List;

import com.example.DbConfiguration;
import com.example.JmsConfig;
import com.example.TicketApplication;
import com.example.order.pojos.Order;
import com.example.order.pojos.Ticket;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { DbConfiguration.class, JmsConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ TicketApplication.class })
public class OrderControllerIT {

    @Value("9000")
    private int port;

    @Autowired
    OrderRepository orderRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testRetrieveOrders() throws Exception {
        Date date = new Date(2022, 20, 20);
        Ticket ticket = new Ticket(1L, "source", "destination", date, 1000L);
        Order order = new Order(1L, "testUsername", ticket);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        this.mockMvc.perform(
                post("/orders", "John Doe").contentType(APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(order)));

        List<Order> all = orderRepository.findAll();
        Order order2 = all.get(0);
        Ticket ticket2 = order2.getTicket();
        assertEquals("testUsername", order2.getUsername());
        assertEquals("source", ticket2.getSource());
        assertEquals("destination", ticket2.getDestination());
        assertEquals(date, ticket2.getDate());
        assertEquals(ticket.getPrice(), ticket2.getPrice());

    }

    @Test
    public void addCourse() {
        Date date = new Date(2022, 20, 20);
        Ticket ticket = new Ticket(1L, "source", "destination", date, 1000L);
        Order order = new Order(1L, "testUsername", ticket);
        HttpEntity<Order> entity = new HttpEntity<Order>(order, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/order"),
                HttpMethod.POST, entity, String.class);

        List<Order> all = orderRepository.findAll();
        Order order2 = all.get(0);
        Ticket ticket2 = order2.getTicket();
        assertEquals("testUsername", order2.getUsername());
        assertEquals("source", ticket2.getSource());
        assertEquals("destination", ticket2.getDestination());
        assertEquals(date, ticket2.getDate());
        assertEquals(ticket.getPrice(), ticket2.getPrice());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
