package com.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {

	DRAFT("In Cart"), WAITING("Waiting to be accepted"), IN_PROCESS("In process"), CANCELLED("Cancelled"),
	COMPLETED("Completed");

	private String value;

}
