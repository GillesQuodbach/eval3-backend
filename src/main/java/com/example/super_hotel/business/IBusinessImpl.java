package com.example.super_hotel.business;

import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class IBusinessImpl implements IBusiness{
@Autowired
    CityRepository cityRepository;
    @Autowired
    HotelRepository hotelRepository;

    // * HOTEL METHOD
    @Override
    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getHotelsByCity(Long cityId){
        City city = cityRepository.findById(cityId).orElse(null);
        return hotelRepository.findByCity(city);
    }

    @Override
    public Optional<Hotel>getHotelById(Long cityId){
        return hotelRepository.findById(cityId);
    }

    @Override
    public Hotel saveHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }

    // * CITY METHOD
    @Override
    public List<City> getCities(){
        return cityRepository.findAll();
    }

    @Override
    public City saveCity(City city){
        return cityRepository.save(city);
    }
}
