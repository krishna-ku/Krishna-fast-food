package com.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponStatus {
	
	ACTIVE("ACTIVE"), REDEEM("REDEEM"),EXPIRED("EXPIRED");
	
	private String value;

}
