package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RatingDto;

public interface RatingService {
	
	//create rating
	RatingDto createRating(RatingDto ratingDto,long orderId,long userId);
	
	//update rating
//	RatingDto updatRating(RatingDto ratingDto,long id);
	
	//delete rating
	void deleteRating(long ratingId);
	
	//get all ratings
	List<RatingDto> getAllRatings();
	
	//get rating by id
	RatingDto getRatingById(long ratingId);
	
	
	
	
	
}
