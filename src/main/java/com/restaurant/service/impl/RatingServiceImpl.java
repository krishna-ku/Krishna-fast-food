package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.RatingDto;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.RatingRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepo ratingRepo;

	@Autowired
	private UserRepo userRepo;

	/**
	 * add Rating.
	 * 
	 * @param RatingDto
	 * @return RatingDto
	 * @see com.restaurant.dto.RatingDto
	 */
	@Override
	public RatingDto createRating(RatingDto ratingDto, long id) {// rating dto

		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, id));

		Rating rating = new Rating(ratingDto);

		rating.setUser(user);

		this.ratingRepo.save(rating);

		return new RatingDto(rating);
	}

	/**
	 * delete Rating
	 * 
	 * @param id
	 * @return void
	 */
	@Override
	public void deleteRating(long id) {

		Rating rating = this.ratingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, id));
		this.ratingRepo.delete(rating);
	}

	/**
	 * return all Ratings
	 * 
	 * @return list of RatingDtos
	 * 
	 * @see com.restaurant.dto.RatingDto
	 */
	@Override
	public List<RatingDto> getAllRatings() {

		List<Rating> ratings = this.ratingRepo.findAll();

//		List<RatingDto> ratingDtos = ratings.stream().map(rating -> ratingToDto(rating)).collect(Collectors.toList());
		return ratings.stream().map(rating -> new RatingDto(rating)).collect(Collectors.toList());
//		return ratings.stream().map(new::Rating).collect(Collectors.toList());
	}

	/**
	 * return Rating by id
	 * 
	 * @param id
	 * @return RatingDto by id
	 * @see com.restaurant.dto.RatingDto
	 */
	@Override
	public RatingDto getRatingById(long id) {

		Rating rating = this.ratingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, id));

		return new RatingDto(rating);
	}

}
