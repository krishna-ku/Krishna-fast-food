package com.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailStatus {
	
	PENDING("PENDING"), SENT("SENT"), FAILED("FAILED");
	
	private String value;
}
