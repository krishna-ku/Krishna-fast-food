package com.restaurant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.RestaurantDto;
import com.restaurant.entity.Restaurant;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepo restaurantRepo;

	@Override
	public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
		
		Restaurant newRestaurant=new Restaurant(restaurantDto);
		
		return new RestaurantDto(restaurantRepo.save(newRestaurant));
	}

	@Override
	public RestaurantDto updateUser(RestaurantDto restauratDto, Long restaurantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(long restaurantId) {
		// TODO Auto-generated method stub

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
