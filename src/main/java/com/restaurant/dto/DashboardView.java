package com.restaurant.dto;

public interface DashboardView {

	Integer getTotalUsers();
	Integer getTotalOrders();
	Integer getCompletedOrders();
	Integer getCancelledOrders();
	Integer getWaitingOrders();

}
