package com.restaurant.entity;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Menu extends SuperClass {

	private String name;

	private float price;

	private String description;

////	@OneToOne
//	@JoinColumn(name = "orders_id")
//	private Order orders;
//	
//	@OneToMany(mappedBy = "menu",cascade = CascadeType.ALL)
//	List<OrderItem> orderItems;

}
