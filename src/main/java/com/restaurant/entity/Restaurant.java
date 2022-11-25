package com.restaurant.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.dto.RestaurantDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Restaurant SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@FilterDef(name = "deletedRestaurantFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedRestaurantFilter", condition = "deleted = :isDeleted")
public class Restaurant extends BaseClass {

	private String name;

	private String address;

	private String description;

	private String contactNo;


//	@Column(columnDefinition = "boolean default false") // by default close
	private String status;

	private String openTiming;

	private String closeTiming;

//	private Date openingTime;

//	private Date closingTime;

	public Restaurant(RestaurantDto restaurantDto) {
		this.name = restaurantDto.getName();
		this.address = restaurantDto.getAddress();
		this.description = restaurantDto.getDescription();
		this.contactNo = restaurantDto.getContactNo();
		this.openTiming = restaurantDto.getOpenTiming();
		this.closeTiming = restaurantDto.getCloseTiming();
		
		if("OPEN".equals(restaurantDto.getStatus()))
			this.status=restaurantDto.getStatus();
		else {
			this.status="CLOSE";
		}

//		if(local.equals("09:00"))
//			this.status="OPEN";
//		else
//			status="CLOSE";

//		this.openingTime=new SimpleDateFormat("HH:mm:ss").parse(restaurantDto.getOpenTiming());

	}
}
