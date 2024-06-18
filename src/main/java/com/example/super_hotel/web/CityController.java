package com.example.super_hotel.web;

import com.example.super_hotel.business.IBusinessImpl;
import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
public class CityController {

    @Autowired
    IBusinessImpl iBusinessImpl;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    HotelRepository hotelRepository;


    @GetMapping("/cities")
    public List<City> getAllCities(){
        return iBusinessImpl.getCities();
    }
    @PostMapping("cities")
    public ResponseEntity<City> saveNewHotel(@RequestBody City city){
        City city1 = iBusinessImpl.saveCity(city);
        if(Objects.isNull(city1)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city1.getId())
                .toUri();
        return ResponseEntity.created(location).body(city1);
    }
}
