package com.example.super_hotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_hotel")
@Data
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private int stars;
    private int disponibility;
    private double price;
    private String img;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
