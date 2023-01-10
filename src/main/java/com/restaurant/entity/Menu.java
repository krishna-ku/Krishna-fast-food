package com.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.dto.MenuDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Menu SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class Menu extends BaseClass {

	private String name;

	private float price;

	private String description;
	
	private int dishRating;

//	private int category;

	@OneToOne
	private MenuCategory menuCategory;

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
		this.dishRating=menuDto.getDishRating();
//		this.category = menuDto.getCategory();
	}

}
