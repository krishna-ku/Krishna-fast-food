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


		this.orderItems = order.getOrderItems().stream().map(o -> {
			
			this.totalPrice+=o.getMenu().getPrice()*o.getItemQuantity();

			return new OrderItemDto(o);

		}).collect(Collectors.toList());
		totalPriceWithGst=totalPrice+(totalPrice*Keywords.GST_PERCENTAGE);
//		totalPriceWithGstAndServiceTax+=totalPrice*Keywords.SERVICE_TAX;
	}

	private long orderId;
	@NotEmpty
	private String orderStatus;//use regex for validation this field
	
	private float totalPriceWithGst;
	
	private float totalPrice;

	private String customer;

	// @NotEmpty
	private List<OrderItemDto> orderItems;

	// private Menu menu;
}
