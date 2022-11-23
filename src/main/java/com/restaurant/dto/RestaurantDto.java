package com.restaurant.dto;

import javax.persistence.Column;
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
	private String description;

	@NotEmpty
	private String contactNo;

	private String openTiming;

	private String closeTiming;

//	@Column(columnDefinition = "boolean default false") // by default close
	private String status;

//	private Date openTiming;

//	private Date closingTime;

	public RestaurantDto(Restaurant restaurant) {
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.address = restaurant.getAddress();
		this.description = restaurant.getDescription();
		this.contactNo = restaurant.getContactNo();
		this.openTiming = restaurant.getOpenTiming();
		this.closeTiming = restaurant.getCloseTiming();
		this.status=restaurant.getStatus();
		
//		this.openTiming=restaurant.getOpeningTime();
//		this.closingTime=restaurant.getClosingTime();
//		this.status=restaurant.isStatus()?status.OPEN:status.CLOSE;
//		this.status=restaurant.isStatus()?true:false;
	}

}
