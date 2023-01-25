package com.restaurant.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.PagingDTO;
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
	public PagingDTO<RatingDTO> getAllRatings(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Rating> page = this.ratingRepo.findAll(pageable);
		List<Rating> ratings = page.getContent();
		List<RatingDTO> rating = ratings.stream().filter(m -> !m.isDeleted()).map(RatingDTO::new)
				.collect(Collectors.toList());
		return new PagingDTO<>(rating, page.getTotalElements(), page.getTotalPages());
//		return pagingDTO;
	}

	/**
	 * filter rating on the basis of id,rating and deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@Override
	public List<RatingDTO> filterRatings(RatingDTO ratingDTO, String userName,
			Collection<? extends GrantedAuthority> authorities) {
		Specification<Rating> specification = Specification
				.where(RatingSpecification.filterRatings(ratingDTO, userName, authorities));
		return ratingRepo.findAll(specification).stream().map(r -> new RatingDTO(r)).collect(Collectors.toList());
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
