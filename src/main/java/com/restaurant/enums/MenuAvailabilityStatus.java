package com.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuAvailabilityStatus {

	AVAILABLE("AVAILABLE"), NOTAVAILABLE("NOT AVAILABLE");

	private String value;

}
