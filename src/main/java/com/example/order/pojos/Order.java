package com.example.order.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TICKET_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
    private Ticket ticket;

    public Order(Long id, String username, Ticket ticket) {
        this.id = id;
        this.username = username;
        this.ticket = ticket;
    }

    public Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    //    @JsonCreator
//    public Order(@JsonProperty("username") String username,
//                 @JsonProperty("ticket") Ticket ticket) {
//        this.username = username;
//        this.ticket = ticket;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(username, order.username) && Objects.equals(ticket, order.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, ticket);
    }
}
