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
	
	private long menuId;
	private int itemQuantity;
	
	//Map<String, String> map=new HashMap<>();
	
	@ManyToOne
	//@JoinColumn(name = "orderId")
	private Order order;
	
	@OneToOne
	@JoinColumn(name = "menu_ids")
	private Menu menu;

	public OrderItem(OrderItemDto orderItemDto) {
		this.menuId=orderItemDto.getMenuId();
		this.itemQuantity=orderItemDto.getItemQuantity();
	}
}
