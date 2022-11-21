package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Restaurant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantDto {
	
	private long id;

	@NotEmpty
	private String name;
	
	@NotEmpty
	private String address;
	
	@NotEmpty
	private String about;
	
	public RestaurantDto(Restaurant restaurant) {
		this.id=restaurant.getId();
		this.name=restaurant.getName();
		this.address=restaurant.getAddress();
		this.about=restaurant.getAbout();
	}

}
