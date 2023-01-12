package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.dto.UserDTO;

public interface RestaurantService {

	// create restaurant
	RestaurantDTO createRestaurant(RestaurantDTO restaurantDto);

	// update restaurant
	RestaurantDTO updateRestaurant(RestaurantDTO restauratDto);

	// delete user
	void deleteRestaurant(long restaurantId);

	// get users
	List<RestaurantDTO> getAllRestaurat();

	// get user
	RestaurantDTO getRestauratById(Long restaurantId);

	// get restaurant details
	RestaurantDTO getRestaurantDetails();

	// get all restaurants by header
	List<RestaurantDTO> findAllFilter(boolean isDeleted);

}
