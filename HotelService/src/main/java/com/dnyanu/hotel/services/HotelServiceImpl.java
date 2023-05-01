package com.dnyanu.hotel.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnyanu.hotel.entities.Hotel;
import com.dnyanu.hotel.exceptions.ResourceNotFoundException;
import com.dnyanu.hotel.repositories.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelRepository hotelRepo;

	@Override
	public Hotel createHotel(Hotel hotel) {
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		Hotel hotel1 = hotelRepo.save(hotel);
		return hotel1;
	}

	@Override
	public List<Hotel> getAllHotels() {
		List<Hotel> allHotels = hotelRepo.findAll();
		return allHotels;
	}

	@Override
	public Hotel getHotel(String id) {
		return hotelRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with id = "+id+" not found !"));
	}

}
