package com.example.super_hotel.web;

import com.example.super_hotel.business.IBusinessImpl;
import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.Hotel;
import com.example.super_hotel.exceptions.RecordNotFoundException;
import com.example.super_hotel.services.image.IImageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@CrossOrigin("*")
@RestController
public class HotelController {
    @Autowired
    private IBusinessImpl iBusinessImpl;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    IImageImpl iImageImpl;



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

    @GetMapping("/hotel/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id){
        return iBusinessImpl.getHotelById(id).orElseThrow(()-> new RecordNotFoundException("Id = "+ id + " doesn't match any hotel"));
    }

    @DeleteMapping("/hotels/{id}")
    public void deleteHotel(@PathVariable("id") Long id){
        System.out.println("Suppression ok");
        iBusinessImpl.deleteHotel(id);
    }

    @GetMapping("/hotels/city/{id}")
    public List<Hotel> getHotelsByCity(@PathVariable("id") Long id){
        return iBusinessImpl.getHotelsByCity(id);
    }

    @GetMapping("images/{id}")
    public ResponseEntity<Resource> downloadImg(@PathVariable("id") Long hotelId) throws Exception {
        Optional<Hotel> optionalHotel = iBusinessImpl.getHotelById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            System.out.println(hotel);
            String imgName = hotel.getImg();
            System.out.println(imgName);
            Resource resource =  iBusinessImpl.loadImageAsResource(imgName);
            String contentType = iBusinessImpl.getContentType(imgName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/imaes")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Le fichier est vide.");
            return ResponseEntity.badRequest().body(response);
        }

        String fileName = file.getOriginalFilename();
        try {
            iImageImpl.uploadImage(file);
        } catch (IOException e) {
            response.put("message", "Erreur lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("message", "Une erreur inattendue est survenue lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("message", "Le fichier " + fileName + " a été téléchargé avec succès.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/images/{id}")
    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable("id") Long hotelId, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("message", "Le fichier est vide.");
            return ResponseEntity.badRequest().body(response);
        }
        String fileName = file.getOriginalFilename();

        try {
            iImageImpl.uploadAndAssignImageToHotel(hotelId, file);
        } catch (IOException e) {
            response.put("message", "Erreur lors du téléchargement du fichier " + fileName + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.put("message", "Le fichier " + fileName + " a été téléchargé avec succès et à bien été affilier à la l'hotel :" + hotelId);
        return ResponseEntity.ok(response);
    }


}
