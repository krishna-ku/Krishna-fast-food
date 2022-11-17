package com.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

	// convert order into orderDto
	public OrderDto(Order order) {
		this.customer=order.getCustomer();
		this.orderId = order.getId();
		this.orderStatus = order.getStatus().toString();

//		this.customer = order.getUser();

		this.orderItems = order.getOrderItems().stream().map(o -> {

//			OrderItemDto orderItem= new OrderItemDto(o);
			return new OrderItemDto(o);

		}).collect(Collectors.toList());
	}

	private long orderId;
	@NotEmpty
	private String orderStatus;//use regex for validation this field

	private String customer;

	// @NotEmpty
	private List<OrderItemDto> orderItems;

	// private Menu menu;
}
