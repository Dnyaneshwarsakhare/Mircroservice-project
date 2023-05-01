package com.dnyanu.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dnyanu.user.service.entities.Hotel;
import com.dnyanu.user.service.entities.Rating;
import com.dnyanu.user.service.entities.User;
import com.dnyanu.user.service.exceptions.ResourceNotFoundException;
import com.dnyanu.user.service.repositories.UserRepository;
import com.dnyanu.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		/*
		 * ArrayList<Rating> ratings =
		 * restTemplate.getForObject("http://192.168.0.103:8083/ratings",
		 * ArrayList.class ); List<Rating> ratingList = ratings.stream().map(rating -> {
		 * //rating.getUserId(); //return rating; }).collect(Collectors.toList());
		 */
		List<User> allUser =  userRepo.findAll();
		return allUser;
	}

	@Override
	public User getUser(String userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id="+userId+" not found"));
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map( rating->{
			 ResponseEntity<Hotel> forEntity= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			 Hotel hotel = forEntity.getBody();
			 rating.setHotel(hotel);
			 return rating;
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		return user;
	}
	
	
}
