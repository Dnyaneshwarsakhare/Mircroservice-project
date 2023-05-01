package com.dnyanu.rating.services;

import java.util.List;

import com.dnyanu.rating.entities.Rating;

public interface RatingService {

	Rating createRating(Rating rating);
	List<Rating> getRatings();
	List<Rating> getRatingByUserId(String userId);
	List<Rating> getRatingByHotelId(String hotelId);
}
