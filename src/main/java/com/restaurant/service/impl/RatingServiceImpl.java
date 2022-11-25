package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.RatingDto;
import com.restaurant.entity.Order;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.OrderRepo;
import com.restaurant.repository.RatingRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepo ratingRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrderRepo orderRepo;

	/**
	 * add Rating.
	 * 
	 * @param RatingDto
	 * @param orderId
	 * @param userId
	 * @return RatingDto
	 * @see com.restaurant.dto.RatingDto
	 */
	@Override
	public RatingDto createRating(RatingDto ratingDto, long orderId, long userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));

		Rating rating = new Rating(ratingDto);

		rating.setOrders(order);

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
	public void deleteRating(long ratingId) {

		Rating rating = this.ratingRepo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, ratingId));
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

		return ratings.stream().map(rating -> new RatingDto(rating)).collect(Collectors.toList());
	}

	/**
	 * return Rating by id
	 * 
	 * @param id
	 * @return RatingDto by id
	 * @see com.restaurant.dto.RatingDto
	 */
	@Override
	public RatingDto getRatingById(long ratingId) {

		Rating rating = this.ratingRepo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, ratingId));

		return new RatingDto(rating);
	}

	/**
	 * return lists of deleted and undeleted ratings
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted ratings
	 * @see com.restaurant.entity.Rating
	 */
	public List<RatingDto> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedRatingFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<Rating> rating = ratingRepo.findAll();
		session.disableFilter("deletedRatingFilter");

		return rating.stream().map(u -> new RatingDto(u)).collect(Collectors.toList());
	}

}
