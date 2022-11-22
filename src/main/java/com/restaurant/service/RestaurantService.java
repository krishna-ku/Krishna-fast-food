package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDto;

public interface RestaurantService {

	// create restaurant
	RestaurantDto createRestaurant(RestaurantDto restaurantDto);

	// update restaurant
	RestaurantDto updateRestaurant(RestaurantDto restauratDto, Long restaurantId);

	// delete user
	void deleteRestaurant(long restaurantId);

	// get users
	List<RestaurantDto> getAllRestaurat();

	// get user
	RestaurantDto getRestauratById(Long restaurantId);
	
	//get restaurant details
	RestaurantDto getRestaurantDetails();

}
