package com.restaurant.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	
	/**
	 * add Restaurant.
	 * 
	 * @param RestaurantDto objects
	 * 
	 * @see com.restaurant.dto.RestaurantDto
	 */
	@Override
	public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
				
		if(restaurantDto.getContactNo().length()!=10)
			{throw new BadRequestException("Please enter valid phone number");}
		
		Restaurant restaurant=new Restaurant(restaurantDto);
		
		return new RestaurantDto(restaurantRepo.save(restaurant));
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
		
		Restaurant restaurant = restaurantRepo.findById(restaurantDto.getId()).orElseThrow(()->new ResourceNotFoundException(Keywords.RESTAURANT, Keywords.RESTAURANT_ID, restaurantDto.getId()));
		
//		if(restaurantDto.getContactNo().length()!=10 || restaurantDto.getContactNo().length()!=0)
//		{throw new BadRequestException("Please enter valid phone number");}
		
		if(!StringUtils.isEmpty(restaurantDto.getName()))
			restaurant.setName(restaurantDto.getName());
		
		if(!StringUtils.isEmpty(restaurantDto.getAddress()))
			restaurant.setAddress(restaurantDto.getAddress());
		
		if(!StringUtils.isEmpty(restaurantDto.getDescription()))
			restaurant.setDescription(restaurantDto.getDescription());
		
		if(!StringUtils.isEmpty(restaurantDto.getContactNo()))
			restaurant.setContactNo(restaurantDto.getContactNo());
		
//		if(!StringUtils.isEmpty(restaurantDto.isStatus()))
//			restaurant.setStatus(restaurantDto.isStatus());
		
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
