package com.restaurant.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.restaurant.dto.OrderDTO;
import com.restaurant.dto.OrderItemDTO;
import com.restaurant.dto.PagingDTO;

public interface OrderService {

	// create user
	OrderDTO placedOrder(OrderDTO orderDto, long userId);

	// update user
	OrderDTO updateOrder(List<OrderItemDTO> orderItemDto, Long orderId);

	// delete user
	void deleteOrder(long orderId);

	// get users
	PagingDTO getAllOrders(Integer pageNumber, Integer pageSize);

	// activate orders
	String activateOrder(long orderId);

	// get filter orders
	List<OrderDTO> filterOrders(OrderDTO orderDTO,String userName,Collection<? extends GrantedAuthority> authorities);

	// give order rating
//	List<OrderDTO> getOrdersByRating();

	// repeat order
	OrderDTO repeatOrder(String username, long orderID);

}
