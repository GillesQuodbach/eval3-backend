package com.example.super_hotel.business;

import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface IBusiness {
    // * Hotel methods
    List<Hotel> getHotels();
    List<Hotel> getHotelsByCity(Long cityId);

    Optional<Hotel> getHotelById(Long hotelId);
    Hotel saveHotel(Hotel hotel);

    void deleteHotel(Long hotelId);

    // * City methods
    List<City> getCities();
    City saveCity(City city);

    // * Image methods
    Resource loadImageAsResource(String imgName) throws Exception;
    String getContentType(String imgName) throws Exception;


}
