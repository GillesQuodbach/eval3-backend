package com.example.super_hotel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Represents a City with its hotels.
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_city")
@Data
@Builder
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * The unique identifier for the city.
     */
    private Long id;
    /**
     * The name of the city.
     */
    private String name;
    /**
     * The collection of hotels associated with the city.
     */
    @OneToMany(mappedBy = "city")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Hotel> hotels;

    /**
     * Constructs a new Category with the specified name and collection of trainings.
     *
     * @param name      the name of the city.
     * @param hotels the collection of hotel associated with the city.
     */
    public City(String name, Collection<Hotel> hotels){
        this.name = name;
        this.hotels = hotels;
    }
}
