package com.restaurant.dto;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Keywords {
	
	public static final String USER="User";
	public static final String USER_ID="userId";
	
	public static final String MENU="Menu";
	public static final String MENU_ID="MenuId";
	
	public static final String RATING="Rating";
	public static final String RATING_ID="RatingId";
	
	public static final String ORDER="Order";
	public static final String ORDER_ID="OrderId";
	
	public static final Pattern EMAIL_REGEX=Pattern.compile("[a-z][a-z0-9.]+@[a-z]+.[a-z]{2,6}");//[range of regex] 
	
	public static final float GST_PERCENTAGE=0.18f;
	
	public static final float SERVICE_TAX=0.5f;
}
