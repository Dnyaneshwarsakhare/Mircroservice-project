package com.dnyanu.hotel.services;

import java.util.List;

import com.dnyanu.hotel.entities.Hotel;

public interface HotelService {

	Hotel createHotel(Hotel hotel);
	
	List<Hotel> getAllHotels();
	
	Hotel getHotel(String id);
}
