package com.restaurant.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
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

	private String contactNo;

	@Column(columnDefinition = "boolean default false") // by default close
	private boolean status;

	private Date openingTime;

	private Date closingTime;

	public Restaurant(RestaurantDto restaurantDto){
		this.name = restaurantDto.getName();
		this.address = restaurantDto.getAddress();
		this.about = restaurantDto.getAbout();
		this.contactNo = restaurantDto.getContactNo();
//		this.openingTime=new SimpleDateFormat("HH:mm:ss").parse(restaurantDto.getOpenTiming());
	}
}
