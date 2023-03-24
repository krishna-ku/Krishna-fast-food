package com.restaurant.entity;

import javax.persistence.Entity;

import com.restaurant.dto.MenuAdditionalItemsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuAdditionalItems extends BaseClass {
	
	private String name;
	
	private float price;	
	
	public MenuAdditionalItems(MenuAdditionalItemsDTO menuAdditionalItemsDTO) {
		
		name=menuAdditionalItemsDTO.getName();
		price=menuAdditionalItemsDTO.getPrice();
	}
	
	
}
