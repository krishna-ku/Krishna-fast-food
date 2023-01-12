package com.restaurant.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {

	// convert order into orderDto
	public OrderDTO(Order order) {
		this.orderId = order.getId();
		this.orderStatus = order.getStatus().toString();
		this.restaurantName = order.getRestaurant().getName();
		this.orderDate = new Date();
		this.orderItems = order.getOrderItems().stream().map(o -> {

			this.totalPrice += o.getMenu().getPrice() * o.getItemQuantity();

			return new OrderItemDTO(o);

		}).collect(Collectors.toList());
		totalPriceWithGst = totalPrice + (totalPrice * Keywords.GST_PERCENTAGE);
	}

	private long orderId;

	private String orderStatus;

	private float totalPriceWithGst;

	private String restaurantName;

	private Date orderDate;

	private float totalPrice;


	@Valid
	@NotEmpty
	private List<OrderItemDTO> orderItems;

}
