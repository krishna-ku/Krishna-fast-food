package com.restaurant.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AdditionalItemsOfMenu extends BaseClass {
	
	private String name;
	
	private float price;
	
	
}
