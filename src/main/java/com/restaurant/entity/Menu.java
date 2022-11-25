package com.restaurant.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.restaurant.dto.MenuDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Menu SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@FilterDef(name = "deletedMenuFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedMenuFilter", condition = "deleted = :isDeleted")
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

	public Menu(MenuDto menuDto) {
		this.name = menuDto.getName();
		this.price = menuDto.getPrice();
		this.description = menuDto.getDescription();
	}
}
