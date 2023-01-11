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
@SQLDelete(sql = "UPDATE Menu SET deleted=true, availability='not available' where id=?")
@Where(clause = "deleted=false AND availability='available'")
public class Menu extends BaseClass {

	private String name;

	private float price;

	private String description;

	private int dishRating;

	private String availability = "Available";

	private String imageName;

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
		this.dishRating = menuDto.getDishRating();
		this.availability = menuDto.getAvailability();
		this.imageName = menuDto.getImageName();
//		this.category = menuDto.getCategory();
	}

}
