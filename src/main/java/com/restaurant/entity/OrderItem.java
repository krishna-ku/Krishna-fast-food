package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.restaurant.dto.OrderItemDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class OrderItem extends SuperClass {

//	private long menuId;//orderitem.getmenu.getid
	private int itemQuantity;

//	private String name;

	@ManyToOne
	// @JoinColumn(name = "orderId")
	private Order order;

	@OneToOne
	@JoinColumn(name = "menu_ids")
	private Menu menu;

	public OrderItem(OrderItemDto orderItemDto) {
//		this.menuId=orderItemDto.getMenuId();
		this.itemQuantity = orderItemDto.getItemQuantity();

	}
}
