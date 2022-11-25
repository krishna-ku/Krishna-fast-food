package com.restaurant.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.Keywords;
import com.restaurant.dto.RestaurantDto;
import com.restaurant.entity.Restaurant;
import com.restaurant.exception.BadRequestException;
import com.restaurant.exception.ResourceNotFoundException;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepo restaurantRepo;

	@Autowired
	private EntityManager entityManager;

	/**
	 * add Restaurant.
	 * 
	 * @param RestaurantDto objects
	 * 
	 * @see com.restaurant.dto.RestaurantDto
	 */
	@Override
	public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
		
		Restaurant restaurant=restaurantRepo.findByContactNo(restaurantDto.getContactNo());
		
		if(restaurant==null)
		{
		if (restaurantDto.getContactNo().length() != 10) {
			throw new BadRequestException("Please enter valid phone number");
		}
		
		Restaurant newRestaurant = new Restaurant(restaurantDto);

		return new RestaurantDto(restaurantRepo.save(newRestaurant));
		}
		else {
		throw new BadRequestException("Restaurant is already registered");
		}
	}

	/**
	 * update Restaurant.
	 * 
	 * @param RestaurantDto
	 * @param restaurantId
	 * @return updated RestaurantDto
	 * @see com.restaurant.dto.RestaurantDto
	 */
	@Override
	public RestaurantDto updateRestaurant(RestaurantDto restaurantDto) {

		Restaurant restaurant = restaurantRepo.findById(restaurantDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException(Keywords.RESTAURANT, Keywords.RESTAURANT_ID,
						restaurantDto.getId()));

		if (!StringUtils.isEmpty(restaurantDto.getName()))
			restaurant.setName(restaurantDto.getName());

		if (!StringUtils.isEmpty(restaurantDto.getAddress()))
			restaurant.setAddress(restaurantDto.getAddress());

		if (!StringUtils.isEmpty(restaurantDto.getDescription()))
			restaurant.setDescription(restaurantDto.getDescription());

		if (!StringUtils.isEmpty(restaurantDto.getContactNo())) {
			if (restaurantDto.getContactNo().length() != 10)
				throw new BadRequestException("Please enter valid phone number");
			restaurant.setContactNo(restaurantDto.getContactNo());
		}
		
		if(!StringUtils.isEmpty(restaurantDto.getStatus()))
			restaurant.setStatus(restaurantDto.getStatus());

		return new RestaurantDto(restaurantRepo.save(restaurant));
	}

	/**
	 * delete Restaurant
	 * 
	 * @param restaurantId
	 * @return void
	 */
	@Override
	public void deleteRestaurant(long restaurantId) {

		restaurantRepo.deleteById(restaurantId);

	}

	/**
	 * return lists of deleted and undeleted restaurant
	 * 
	 * @param isDeleted=true or false
	 * @return list of deleted or undeleted restaurants
	 * @see com.restaurant.entity.Restaurant
	 */
	public List<RestaurantDto> findAllFilter(boolean isDeleted) {
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("deletedRestaurantFilter");
		filter.setParameter("isDeleted", isDeleted);
		List<Restaurant> restaurants = restaurantRepo.findAll();
		session.disableFilter("deletedRestaurantFilter");

		return restaurants.stream().map(u -> new RestaurantDto(u)).collect(Collectors.toList());
	}

	/**
	 * get details of Restaurant
	 * 
	 * @return RestaurantDto
	 */
	@Override
	public RestaurantDto getRestaurantDetails() {

		return new RestaurantDto(restaurantRepo.getById((long) 1));
	}

	@Override
	public List<RestaurantDto> getAllRestaurat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestaurantDto getRestauratById(Long restaurantId) {
		// TODO Auto-generated method stub
		return null;
	}

}
