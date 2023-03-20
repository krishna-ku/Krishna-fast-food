package com.restaurant.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.restaurant.dto.PagingDTO;
import com.restaurant.dto.RatingDTO;

public interface RatingService {

	// create rating
	RatingDTO createRating(RatingDTO ratingDto, long orderId, long userId);

	// update rating
//	RatingDto updatRating(RatingDto ratingDto,long id);

	// delete rating
	void deleteRating(long ratingId);

	// get all ratings
	PagingDTO getAllRatings(Integer pageNumber, Integer pageSize);

	// make raring active
	String activateRating(long ratingId);
	
	//filter ratings
	List<RatingDTO> filterRatings(RatingDTO ratingDTO,String userName,Collection<? extends GrantedAuthority> authorities);

}
