package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.OrderDto;
import com.restaurant.dto.OrderItemDto;

public interface OrderService {

	// create user
	OrderDto placedOrder(List<OrderItemDto> orderItemDto,long id);

	// update user
	OrderDto updateOrder(List<OrderItemDto> orderItemDto, Long id);

	// delete user
	void deleteOrder(long id);

	// get users
	List<OrderDto> getAllOrders();

	// get user
	OrderDto getOrderById(Long id);

}
