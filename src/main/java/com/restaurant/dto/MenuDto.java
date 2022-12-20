package com.restaurant.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuDTO {

	private long id;
	@NotEmpty
	private String name;

	@Range(min = 1, max = 999)
	private float price;// float or double

//	@Pattern(regexp = "[A-Za-z]*$",message = "size must be between 10 to 100 alphabets")//if else check manually
	@Size(min = 10, max = 100, message = "size must be between 10 to 100 alphabets")
	private String description;// size validation

	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.description = menu.getDescription();
	}

	@Override
	public int hashCode() {
		int p=97;
		int r=p+name.hashCode();
		r=p*r+(int) id;
		return r;
	}
	
	
	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else {
			if (obj instanceof MenuDTO) {// instance of
				MenuDTO menuDTO = (MenuDTO) obj;
				if (this.id == menuDTO.getId() && this.name == menuDTO.getName()) {
					return true;
				}
			}

		}
		return false;
	}

	public MenuDTO(String name, Float price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public String toString() {
		return "Menu [name=" + name + ", price=" + price + ", desc=" + description + "]";
	}
}
