package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.OrderDto;
import com.restaurant.dto.OrderItemDto;

public interface OrderService {

	// create user
	OrderDto placedOrder(OrderDto orderDto,long userId);

	// update user
	OrderDto updateOrder(List<OrderItemDto> orderItemDto, Long orderId);

	// delete user
	void deleteOrder(long orderId);

	// get users
	List<OrderDto> getAllOrders();

	// get user
	OrderDto getOrderById(Long orderId);

}
