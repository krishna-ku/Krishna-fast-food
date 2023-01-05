package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RatingDTO;

public interface RatingService {

	// create rating
	RatingDTO createRating(RatingDTO ratingDto, long orderId, long userId);

	// update rating
//	RatingDto updatRating(RatingDto ratingDto,long id);

	// delete rating
	void deleteRating(long ratingId);

	// get all ratings
	List<RatingDTO> getAllRatings();

	// make raring active
	String activateRating(long ratingId);
	
	//filter ratings
	List<RatingDTO> filterRatings(RatingDTO ratingDTO);

}
