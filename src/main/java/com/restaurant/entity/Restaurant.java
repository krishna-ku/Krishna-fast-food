package com.restaurant.entity;

import javax.persistence.Entity;

import com.restaurant.dto.RestaurantDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Restaurant extends BaseClass {
		
	private String name;
	
	private String address;
	
	private String about;	
	
	public Restaurant(RestaurantDto restaurantDto) {
		this.name=restaurantDto.getName();
		this.address=restaurantDto.getAddress();
		this.about=restaurantDto.getAbout();
	}
}
