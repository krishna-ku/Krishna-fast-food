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
	public RatingDto createRating(RatingDto ratingDto, long id) {

		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, id));

		Rating rating = this.dtoToRating(ratingDto);

		rating.setUser(user);

		Rating saveRating = this.ratingRepo.save(rating);

		return this.ratingToDto(saveRating);
	}

	/**
	 * update Rating
	 * 
	 * @param id
	 * @return updated Rating object
	 * @see com.restaurant.dto.RatingDto
	 */
//	@Override
//	public RatingDto updatRating(RatingDto ratingDto, long id) {
//
//		Rating rating = this.ratingRepo.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Rating", "Rating id", id));
//		rating.setRating(ratingDto.getRating());
//		rating.setReview(ratingDto.getReview());
//		Rating updateRating = this.ratingRepo.save(rating);
//
//		return this.ratingToDto(updateRating);
//	}

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

		return ratings.stream().map(this::ratingToDto).collect(Collectors.toList());
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

		return ratingToDto(rating);
	}

	/**
	 * convert RatingDto object into Rating
	 * 
	 * @param RatingDto
	 * @return Rating
	 * @see com.restaurant.entity.Rating
	 */
	private Rating dtoToRating(RatingDto ratingDto) {

		Rating rating = new Rating();
		rating.setId(ratingDto.getId());
		rating.setRating(ratingDto.getRating());
		rating.setReview(ratingDto.getReview());

		return rating;
	}

	/**
	 * convert Rating into RatingDto
	 * 
	 * @param Rating
	 * @return RatingDto
	 * @see com.restaurant.dto.RatingDto
	 */
	private RatingDto ratingToDto(Rating rating) {
		RatingDto ratingDto = new RatingDto();
		ratingDto.setId(rating.getId());
		ratingDto.setRating(rating.getRating());
		ratingDto.setReview(rating.getReview());

		return ratingDto;
	}

}
