package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderItemDTO;
import com.restaurant.dto.UserDTO;

public interface OrderService {

	// create user
	OrderDTO placedOrder(OrderDTO orderDto, long userId);

	// update user
	OrderDTO updateOrder(List<OrderItemDTO> orderItemDto, Long orderId);

	// delete user
	void deleteOrder(long orderId);

	// get users
	List<OrderDTO> getAllOrders();

	// get user
	OrderDTO getOrderById(Long orderId);

	// get orders by header
	List<OrderDTO> findAllFilter(boolean isDeleted);
	
//	//get orders by date
//	List<E>

}
