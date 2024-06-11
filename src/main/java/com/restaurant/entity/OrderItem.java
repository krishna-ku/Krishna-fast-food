package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.dto.OrderItemDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE order_item SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class OrderItem extends BaseClass {

//	private long menuId;//orderitem.getmenu.getid
	private int itemQuantity;

	@ManyToOne
	// @JoinColumn(name = "orderId")
	private Order order;

	@OneToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;
	
//	@OneToOne
//	private AdditionalItemsOfMenu itemsOfMenu;

	public OrderItem(OrderItemDTO orderItemDto) {
//		this.menuId=orderItemDto.getMenuId();
		this.itemQuantity = orderItemDto.getItemQuantity();

	}
}
