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
	
	public static final Pattern EMAIL_REGEX=Pattern.compile("[A-Za-z0-9]+@gmail.com");
}
