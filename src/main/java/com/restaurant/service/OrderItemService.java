package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.OrderItemDTO;

public interface OrderItemService {
	
	//placed order items
//	OrderDto placedOrder(List<OrderItemDto> orderItemDto);
	
	//update order items
//	OrderItemDto updateOrder(OrderItemDto orderItemDto,long id);
	
	//delete order items
	void deleteOrder(long id);
	
	//get all orders
	List<OrderItemDTO> getAllOrders();
	
	//get order by id
	OrderItemDTO getOrderById(long id);
	
}
