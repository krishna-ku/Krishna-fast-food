package com.restaurant.entity;

import javax.persistence.Entity;

import com.restaurant.dto.MenuDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Menu extends BaseClass {

	private String name;

	private float price;

	private String description;

////	@OneToOne
//	@JoinColumn(name = "orders_id")
//	private Order orders;
//	
//	@OneToMany(mappedBy = "menu",cascade = CascadeType.ALL)
//	List<OrderItem> orderItems;

	public Menu(MenuDTO menuDto) {
		this.name = menuDto.getName();
		this.price = menuDto.getPrice();
		this.description = menuDto.getDescription();
	}

}
