package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.RatingDTO;
import com.restaurant.entity.Order;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.OrderRepo;
import com.restaurant.repository.RatingRepo;
import com.restaurant.repository.UserRepo;
import com.restaurant.service.RatingService;
import com.restaurant.specification.RatingSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	 * @param RatingDTO
	 * @param orderId
	 * @param userId
	 * @return RatingDto
	 * @see com.restaurant.dto.RatingDTO
	 */
	@Override
	public RatingDTO createRating(RatingDTO ratingDto, long orderId, long userId) {

		log.info("Creating rating for {} ", ratingDto);

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.USER, Keywords.USER_ID, userId));

		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.ORDER, Keywords.ORDER_ID, orderId));

		Rating rating = new Rating(ratingDto);

		rating.setOrders(order);

		rating.setUser(user);

		this.ratingRepo.save(rating);

		log.info("Rating created successfully");

		return new RatingDTO(rating);
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
	 * @see com.restaurant.dto.RatingDTO
	 */
	@Override
	public List<RatingDTO> getAllRatings() {

		List<Rating> ratings = this.ratingRepo.findAll();

		return ratings.stream().map(rating -> new RatingDTO(rating)).collect(Collectors.toList());
	}

	/**
	 * filter rating on the basis of id,rating and deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public List<RatingDTO> filterRatings(RatingDTO ratingDTO) {
		Specification<Rating> specification = Specification.where(RatingSpecification.filterRatings(ratingDTO));
		return ratingRepo.findAll(specification).stream().map(r -> new RatingDTO(r)).collect(Collectors.toList());
	}

	/**
	 * return all Ratings whose ratingValue<=given rating value like 3,4 etc
	 * 
	 * @return list of RatingDtos
	 * 
	 * @see com.restaurant.dto.RatingDTO
	 */
	public List<RatingDTO> ratingsByFilter(int ratingValue) {

		List<Rating> ratings = this.ratingRepo.ratingsByFilter(ratingValue);

		return ratings.stream().map(rating -> new RatingDTO(rating)).collect(Collectors.toList());
	}

	/**
	 * return Rating by id
	 * 
	 * @param id
	 * @return RatingDto by id
	 * @see com.restaurant.dto.RatingDTO
	 */
	@Override
	public RatingDTO getRatingById(long ratingId) {

		Rating rating = this.ratingRepo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, ratingId));

		return new RatingDTO(rating);
	}

	/**
	 * return lists of deleted and undeleted ratings
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted ratings
	 * @see com.restaurant.entity.Rating
	 */
	public List<RatingDTO> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedRatingFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<Rating> rating = ratingRepo.findAll();
		session.disableFilter("deletedRatingFilter");

		return rating.stream().map(u -> new RatingDTO(u)).collect(Collectors.toList());
	}

	/**
	 * activate the deleted rating
	 * 
	 * @param ratingId
	 * @return String
	 * @see com.restaurant.entity.Rating
	 */
	@Override
	public String activateRating(long ratingId) {

		Rating rating = ratingRepo.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RATING, Keywords.RATING_ID, ratingId));
		rating.setDeleted(false);
		ratingRepo.save(rating);
		return "Rating is active";
	}

}
