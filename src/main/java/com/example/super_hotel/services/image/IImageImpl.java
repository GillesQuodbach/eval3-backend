package com.example.super_hotel.services.image;

import com.example.super_hotel.business.IBusinessImpl;
import com.example.super_hotel.entities.Hotel;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Service implementation for handling image-related operations.
 */
@Service
public class IImageImpl implements IImage {

    @Autowired
    private IBusinessImpl iBusinessImpl;

    @Autowired
    private Environment env;

    private String BASE_PATH;

    /**
     * Initializes the base path for storing images.
     */
    @PostConstruct
    public void init() {
        String userHome = env.getProperty("app.home");
        BASE_PATH = userHome + File.separator + "Pictures" + File.separator + "hotels" + File.separator + "images";
    }

    /**
     * Loads an image as a Resource.
     *
     * @param imgName the name of the image.
     * @return the loaded image as a Resource.
     * @throws Exception if an error occurs while loading the image.
     */
    @Override
    public Resource loadImageAsResource(String imgName) throws Exception {
        imgName = cleanPath(imgName); // Nettoyer le nom de fichier
        Path imagePath = Paths.get(BASE_PATH).resolve(imgName);
        Resource resource = new UrlResource(imagePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new Exception("La lecture du fichier est impossible");
        }
    }

    /**
     * Retrieves the content type of an image.
     *
     * @param imgName the name of the image.
     * @return the content type of the image.
     * @throws Exception if an error occurs while retrieving the content type.
     */
    @Override
    public String getContentType(String imgName) throws Exception {
        imgName = cleanPath(imgName); // Nettoyer le nom de fichier
        Path path = Paths.get(BASE_PATH).resolve(imgName);
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    /**
     * Uploads an image.
     *
     * @param file the image file to upload.
     * @throws IOException if an I/O error occurs while uploading the image.
     */
    @Override
    public void uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier n'a été sélectionné.");
        }
        String filename = cleanPath(file.getOriginalFilename());
        file.transferTo(new File(BASE_PATH + File.separator + filename));
    }

    /**
     * Uploads and assigns an image to a hotel.
     *
     * @param hotelId the ID of the hotel.
     * @param file       the image file to upload.
     * @return the filename of the uploaded image.
     * @throws IOException if an I/O error occurs while uploading the image.
     */
    @Override
    public String uploadAndAssignImageToHotel(Long hotelId, MultipartFile file) throws IOException {
        Optional<Hotel> optionalHotel = iBusinessImpl.getHotelById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            if (file.isEmpty()) {
                throw new IllegalArgumentException("Aucun fichier n'a été sélectionné.");
            }
            String filename = cleanPath(file.getOriginalFilename());
            file.transferTo(new File(BASE_PATH + File.separator + filename));
            hotel.setImg(filename);
            iBusinessImpl.saveHotel(hotel);
            return filename;
        } else {
            throw new IllegalArgumentException("La formation avec l'identifiant " + hotelId + " n'existe pas.");
        }
    }

    /**
     * Utility method to clean the path of illegal characters.
     *
     * @param path the original path.
     * @return the cleaned path.
     */
    private String cleanPath(String path) {
        return path.replaceAll("[<>:\"/\\\\|?*]", "");
    }
}
