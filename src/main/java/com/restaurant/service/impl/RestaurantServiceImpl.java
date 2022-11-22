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
				
		if(restaurantDto.getContactNo().length()<10 || restaurantDto.getContactNo().length()>10)
			{throw new BadRequestException("Please enter valid phone number");}
		
		Restaurant restaurant=new Restaurant(restaurantDto);
//		Date date= new SimpleDateFormat("HH:mm:ss").parse(restaurant.getOpeningTime());
		
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
	public RestaurantDto updateRestaurant(RestaurantDto restaurantDto, Long restaurantId) {
		
		Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(()->new ResourceNotFoundException(Keywords.RESTAURANT, Keywords.RESTAURANT_ID, restaurantId));
		
		if(restaurantDto.getContactNo().length()<10 || restaurantDto.getContactNo().length()>10)
		{throw new BadRequestException("Please enter valid phone number");}
		
		if(!StringUtils.isEmpty(restaurantDto.getName()))
			restaurant.setName(restaurantDto.getName());
		
		if(!StringUtils.isEmpty(restaurantDto.getAddress()))
			restaurant.setAddress(restaurantDto.getAddress());
		
		if(!StringUtils.isEmpty(restaurantDto.getAbout()))
			restaurant.setAddress(restaurantDto.getAddress());
		
		if(!StringUtils.isEmpty(restaurantDto.getContactNo()))
			restaurant.setContactNo(restaurantDto.getContactNo());
		
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
