package com.example.super_hotel.web;

import com.example.super_hotel.business.IBusinessImpl;
import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class HotelController {
    @Autowired
    private IBusinessImpl iBusinessImpl;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    HotelRepository hotelRepository;



    @GetMapping("/hotels")
    public List<Hotel> getAllHotels(){
        return iBusinessImpl.getHotels();
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> saveNewHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = iBusinessImpl.saveHotel(hotel);
        if(Objects.isNull(hotel1)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotel1.getId())
                .toUri();
        return ResponseEntity.created(location).body(hotel1);
    }

    @DeleteMapping("/hotels/{id}")
    public void deleteHotel(@PathVariable("id") Long id){
        System.out.println("Suppression ok");
        iBusinessImpl.deleteHotel(id);
    }

    @GetMapping("images/{id}")
    public ResponseEntity<Resource> downloadImg(@PathVariable("id") Long hotelId) throws Exception {
        Optional<Hotel> optionalHotel = iBusinessImpl.getHotelById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            String imgName = hotel.getImg();
            Resource resource =  iBusinessImpl.loadImageAsResource(imgName);
            String contentType = iBusinessImpl.getContentType(imgName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/hotels/city/{id}")
    public List<Hotel> getHotelsByCity(@PathVariable("id") Long id){
        return iBusinessImpl.getHotelsByCity(id);
    }
}
