package com.example.super_hotel.business;

import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class IBusinessImpl implements IBusiness{
    private String BASE_PATH;
@Autowired
    CityRepository cityRepository;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    private Environment env;
    @PostConstruct
    public void init() {
        String userHome = env.getProperty("app.home");
        BASE_PATH = userHome + File.separator + "Pictures" + File.separator + "hotels" + File.separator + "images";
    }
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

    // * Image methods
    @Override
    public Resource loadImageAsResource(String imgName) throws Exception {
        Path imagePath = Paths.get(BASE_PATH).resolve(imgName);
        System.out.println(imagePath);
        Resource resource = new UrlResource(imagePath.toUri());
        System.out.println(resource);
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("La lecture du fichier est impossible");
        }
    }
    @Override
    public String getContentType(String imgName) throws Exception {
        Path path = Paths.get(BASE_PATH).resolve(imgName);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

}
