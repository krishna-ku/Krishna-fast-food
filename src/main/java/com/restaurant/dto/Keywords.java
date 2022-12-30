package com.restaurant.dto;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Keywords {
	
	public static long number=1000;

	public static final String USER = "User";
	public static final String USER_ID = "userId";

	public static final String MENU = "Menu";
	public static final String MENU_ID = "MenuId";

	public static final String RATING = "Rating";
	public static final String RATING_ID = "RatingId";

	public static final String ORDER = "Order";
	public static final String ORDER_ID = "OrderId";
	
	public static final String RESTAURANT="Restaurant";
	public static final String RESTAURANT_ID="RestaurantId";

	public static final Pattern EMAIL_REGEX = Pattern.compile("[a-z][a-z0-9.]+@[a-z]+.[a-z]{2,6}");

	public static final float GST_PERCENTAGE = 0.18f;

	public static final float SERVICE_TAX = 0.5f;
	
	//Restaurant related constants
	public static String BOTTOM_LINE="Rain or shine, it's time to dine";
	public static String RESTAURANT_NAME="Delhi fast food";
	public static String CUSTOMER_NAME="Customer Name: ";
	public static String STATUS="Status: ";
	public static String ORDERID="OrderId: ";
	public static String DATE="Date: ";
	public static String TIME="Time: ";
	public static String SI_NO="Si.No.";
	public static String ITEMS="Items";
	public static String PRICE="Price";
	public static String QUANTITY="Qty";
	public static String TOTAL="Total";
	public static String SUB_TOTAL="Sub Total";
	public static String GST="GST";
	public static String GST_VALUE="18%";
	public static String GRAND_TOTAL="Grand Total";
	public static String PAYMENT_METHODS="Cash, Phonepe, GPay, RuPay, Visa";
	public static String AUTHORIZED_SIGNATORY="Authorized Signatory";
	
}
