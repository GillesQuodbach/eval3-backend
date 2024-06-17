package com.example.super_hotel.dao;

import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    public List<Hotel> findByCity(City city);
}
