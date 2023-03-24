package com.restaurant.dto;

import com.restaurant.entity.MenuAdditionalItems;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuAdditionalItemsDTO {
	
	private String name;
	
	private float price;
	
	public MenuAdditionalItemsDTO(MenuAdditionalItems menuAdditionalItems ) {
		
		this.name=menuAdditionalItems.getName();
		this.price=menuAdditionalItems.getPrice();
	}
	
	
}
