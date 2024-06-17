package com.example.super_hotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_order_item")
@Data
@ToString
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double price;

    @ManyToOne
    Order order;

    @ManyToOne
    Hotel hotel;
}
