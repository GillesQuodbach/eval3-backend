package com.example.super_hotel;

import com.example.super_hotel.dao.CityRepository;
import com.example.super_hotel.dao.HotelRepository;
import com.example.super_hotel.entities.City;
import com.example.super_hotel.entities.Hotel;
import com.example.super_hotel.security.entities.AppRole;
import com.example.super_hotel.security.entities.AppUser;
import com.example.super_hotel.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.ArrayList;
import java.util.List;
@SpringBootApplication

public class SuperHotelApplication implements CommandLineRunner {
	@Value("${app.home}")
	private String userHome;

	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AccountService accountService;


	public static void main(String[] args) {
		SpringApplication.run(SuperHotelApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		generadtedData();
		generateUsersRoles();
	}

	public void generadtedData(){

		// Création des listes
		List<Hotel> hotelsList = new ArrayList<>();

		// Création des villes
		City Paris = new City("Paris",hotelsList);
		City Moscou = new City("Moscou",hotelsList);
		City Berlin = new City("Berlin",hotelsList);
		City Rome = new City("Rome",hotelsList);
		City Madrid = new City("Madrid",hotelsList);

		cityRepository.save(Paris);
		cityRepository.save(Moscou);
		cityRepository.save(Berlin);
		cityRepository.save(Rome);
		cityRepository.save(Madrid);

		hotelRepository.save(new Hotel(null, "Grand Plaza Hotel", "0804050668", 5,10,500,"default.jpg", Paris));
		hotelRepository.save(new Hotel(null, "Sunset Paradise Inn", "0804050668", 4,20,111,"default.jpg", Berlin));
		hotelRepository.save(new Hotel(null, "Blue Lagoon Resort", "0804050668", 3,6,230,"default.jpg", Madrid));
		hotelRepository.save(new Hotel(null, "Majestic Heights Hotel", "0804050668", 3,50,530,"default.jpg", Rome));
		hotelRepository.save(new Hotel(null, "Ocean View Retreat", "0804050668", 4,6,50,"default.jpg", Paris));
		hotelRepository.save(new Hotel(null, "Mountain Ridge Lodge", "0804050668", 4,7,300,"default.jpg", Moscou));
		hotelRepository.save(new Hotel(null, "Silver Sands Resort", "0804050668", 4,33,100,"default.jpg", Berlin));
		hotelRepository.save(new Hotel(null, "Crystal Waters Hotel", "0804050668", 4,30,150,"default.jpg", Rome));
		hotelRepository.save(new Hotel(null, "Royal Palm Inn", "0804050668", 3,2,90,"default.jpg", Madrid));
		hotelRepository.save(new Hotel(null, "Golden Gate Hotel", "0804050668", 3,0,99,"default.jpg", Paris));
		hotelRepository.save(new Hotel(null, "Emerald Isle Resort", "0804050668", 3,22,80,"default.jpg", Madrid));
		hotelRepository.save(new Hotel(null, "Whispering Pines Lodge", "0804050668", 5,11,1000,"default.jpg", Berlin));
		hotelRepository.save(new Hotel(null, "Starlight Shores Hotel", "0804050668", 3,40,77,"default.jpg", Moscou));
		hotelRepository.save(new Hotel(null, "Sunrise Vista Inn", "0804050668", 3,33,70,"default.jpg", Rome));
		hotelRepository.save(new Hotel(null, "Tranquil Meadows Resort", "0804050668", 3,12,50,"default.jpg", Paris));
		hotelRepository.save(new Hotel(null, "Desert Oasis Hotel", "0804050668", 4,11,150,"default.jpg", Berlin));
		hotelRepository.save(new Hotel(null, "Moonlit Bay Inn", "0804050668", 4,10,60,"default.jpg", Moscou));
		hotelRepository.save(new Hotel(null, "Velvet Sky Resort", "0804050668", 4,21,100,"default.jpg", Rome));
		hotelRepository.save(new Hotel(null, "Cascade Falls Lodge", "0804050668", 3,25,200,"default.jpg", Berlin));
		hotelRepository.save(new Hotel(null, "Heavenly Harbor Hotel", "0804050668", 4,30,121,"default.jpg", Madrid));
	}

	private void generateUsersRoles(){
		accountService.saveUser(new AppUser(null,"gilles", "1234", new ArrayList<>()));
		accountService.saveUser(new AppUser(null,"lara", "1234", new ArrayList<>()));
		accountService.saveUser(new AppUser(null,"lary", "1234", new ArrayList<>()));

		accountService.saveRole(new AppRole(null,"SUPERVISOR"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.saveRole(new AppRole(null,"MANAGER"));

		accountService.addRoleToUser("gilles", "SUPERVISOR");
		accountService.addRoleToUser("lara", "MANAGER");
		accountService.addRoleToUser("lary", "USER");

	}

}
