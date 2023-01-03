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

	// get rating by id
	RatingDTO getRatingById(long ratingId);

	// get all deleted and undeleted ratings by header
	List<RatingDTO> findAllFilter(boolean isDeleted);

	// get ratings by filter
	List<RatingDTO> ratingsByFilter(int ratingValue);

	// make user active
	String activateRating(long ratingId);
	
	//filter ratings
	List<RatingDTO> filterRatings(RatingDTO ratingDTO);

}
