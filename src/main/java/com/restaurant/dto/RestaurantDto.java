package com.restaurant.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Restaurant;
import com.restaurant.enums.RestaurantStatus;

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

	@NotEmpty
	private String contactNo;
	
	private String status;
	
	@Enumerated(EnumType.STRING)
	private RestaurantStatus restaurantStatus;

	private Date openTiming;

	private Date closingTime;

	public RestaurantDto(Restaurant restaurant) {
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.address = restaurant.getAddress();
		this.about = restaurant.getAbout();
		this.contactNo=restaurant.getContactNo();
		this.openTiming=restaurant.getOpeningTime();
		this.closingTime=restaurant.getClosingTime();
		this.status=restaurant.isStatus()?RestaurantStatus.OPEN.toString():RestaurantStatus.CLOSE.toString();
	}

}
