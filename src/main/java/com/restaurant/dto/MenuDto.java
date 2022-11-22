package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.Menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDto {

	private long id;
	@NotEmpty
	private String name;

	@Range(min = 1, max = 999)
	private float price;// float or double

//	@Pattern(regexp = "[A-Za-z]*$",message = "size must be between 10 to 100 alphabets")//if else check manually
	@Size(min = 10, max = 100, message = "size must be between 10 to 100 alphabets")
	private String description;// size validation

	public MenuDto(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.description = menu.getDescription();
	}
}
