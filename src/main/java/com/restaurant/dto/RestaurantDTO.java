package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Restaurant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantDTO {

	private long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String address;

	@NotEmpty
	private String description;

	@NotEmpty
	private String contactNo;

	private String openTiming;

	private String closeTiming;

	private String status;

	public RestaurantDTO(Restaurant restaurant) {
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.address = restaurant.getAddress();
		this.description = restaurant.getDescription();
		this.contactNo = restaurant.getContactNo();
		this.openTiming = restaurant.getOpenTiming();
		this.closeTiming = restaurant.getCloseTiming();
		this.status = restaurant.getStatus();
	}

}
