package com.restaurant.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.restaurant.entity.Order;
import com.restaurant.entity.Restaurant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

	// convert order into orderDto
	public OrderDto(Order order) {
		this.customer = order.getCustomer();
		this.orderId = order.getId();
		this.orderStatus = order.getStatus().toString();
		this.restaurantName = order.getRestaurant().getName();
		this.orderDate = new Date();
		this.orderItems = order.getOrderItems().stream().map(o -> {

			this.totalPrice += o.getMenu().getPrice() * o.getItemQuantity();

			return new OrderItemDto(o);

		}).collect(Collectors.toList());
		totalPriceWithGst = totalPrice + (totalPrice * Keywords.GST_PERCENTAGE);
//		totalPriceWithGstAndServiceTax+=totalPrice*Keywords.SERVICE_TAX;
	}

	private long orderId;

	private String orderStatus;// use regex for validation this field

	private float totalPriceWithGst;

	private String restaurantName;

	private Date orderDate;

	private float totalPrice;

	private String customer;

	@Valid
	@NotEmpty
	private List<OrderItemDto> orderItems;

	// private Menu menu;
}
