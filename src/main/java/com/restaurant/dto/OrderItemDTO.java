package com.restaurant.dto;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.OrderItem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {

	public OrderItemDTO(OrderItem o) {
		this.menuId = o.getMenu().getId();
		this.itemQuantity = o.getItemQuantity();
		this.name = o.getMenu().getName();
		this.pricePerItem = o.getMenu().getPrice();
		this.id = o.getId();

	}

	private long id;

	private String name;

	private float pricePerItem;

	@Range(min = 1, max = 10, message = "Item quantity should not be less than 1 or more than 10")
	private int itemQuantity;

	private long menuId;

}
