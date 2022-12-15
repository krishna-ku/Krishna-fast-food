package com.restaurant.dto;

public interface DashboardView {

	int getTotalUsers();
	int getTotalOrders();
	int getCompletedOrders();
	int getCancelledOrders();
	int getWaitingOrders();

}
