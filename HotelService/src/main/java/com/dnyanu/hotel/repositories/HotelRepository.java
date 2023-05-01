package com.dnyanu.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnyanu.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String>{

	
}
