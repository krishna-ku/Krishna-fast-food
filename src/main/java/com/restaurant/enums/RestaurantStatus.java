package com.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RestaurantStatus {

	OPEN("Restaurant is open"), CLOSE("Restaurant is close");

	private String value;

}
