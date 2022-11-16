package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.Menu;
import com.restaurant.entity.OrderItem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {

	public OrderItemDto(OrderItem o) {
		this.menuId = o.getMenuId();
		this.itemQuantity = o.getItemQuantity();
		this.id = o.getId();

	}

	private long id;

	@NotEmpty
	@Range(min = 1, max = 10, message = "maximum 10 itemQuantity you can add")
	private int itemQuantity;

	@NotEmpty
//	@Pattern(regexp = "[0-9]*$")
	private long menuId;

}
