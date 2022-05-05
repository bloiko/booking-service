package com.example.order.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    protected Long id;

    @Column(name = "source")
    protected String source;

    @Column(name = "destination")
    protected String destination;

    @Column(name = "trip_date")
    protected Date date;

    @Column(name = "price")
    protected Long price;

    public Ticket(Long id, String source, String destination, Date date, Long price) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.price = price;
    }

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    //    @JsonCreator
//    public Ticket(@JsonProperty("source") String source,
//                  @JsonProperty("destination") String destination,
//                  @JsonProperty("date") Date date,
//                  @JsonProperty("price") Long price) {
//        this.source = source;
//        this.destination = destination;
//        this.date = date;
//        this.price = price;
//    }
}
