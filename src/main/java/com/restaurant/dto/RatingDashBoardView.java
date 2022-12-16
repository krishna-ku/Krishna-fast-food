package com.restaurant.dto;

public interface RatingDashBoardView {
	
	int getTotalUsersWhoseGivesRating();
	int getTotalUsersInDB();
	int getDistinctOrderId();
}
