package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderItemDTO;

public interface OrderService {

	// create user
	OrderDTO placedOrder(OrderDTO orderDto, long userId);

	// update user
	OrderDTO updateOrder(List<OrderItemDTO> orderItemDto, Long orderId);

	// delete user
	void deleteOrder(long orderId);

	// get users
	List<OrderDTO> getAllOrders(Integer pageNumber,Integer pageSize);

	// activate orders
	String activateOrder(long orderId);

	//get filter orders
	List<OrderDTO> filterOrders(OrderDTO orderDTO);
	
	//give order rating
//	List<OrderDTO> getOrdersByRating();
	
	//repeat order
	OrderDTO repeatOrder(long userId,long orderID);

}
