package com.restaurant.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.restaurant.entity.Menu;
import com.restaurant.enums.MenuAvailabilityStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuDTO {

	private long id;
	@NotEmpty(message = "dish name not be empty")
	private String name;

	@Range(min = 1, max = 999)
	private float price;

	@Size(min = 10, max = 100, message = "size must be between 10 to 100 alphabets")
	private String description;

	@Enumerated(EnumType.STRING)
	private MenuAvailabilityStatus availability = MenuAvailabilityStatus.AVAILABLE;

	private String imageName;
	
	@NotEmpty(message = "please specify type normal or add on")
	private String type;

//	@Range(min = 1,message = "category cannot be null")
//	private int category;

//	@Range(min = 1,max = 5,message = "please give rating menu dishes")
	private int dishRating = 5;

	private Boolean deleted;

	private MenuCategoryDTO category;

	public MenuDTO(Menu menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.price = menu.getPrice();
		this.type=menu.getType();
		this.description = menu.getDescription();
		this.deleted = menu.isDeleted();
		this.dishRating = menu.getDishRating();
		this.availability = menu.getAvailability();
		this.imageName = menu.getImageName();
//		this.category = menu.getCategory();
		this.category = new MenuCategoryDTO(menu.getMenuCategory());
	}

	/**
	 * generating a hascode for an object
	 */
	@Override
	public int hashCode() {
		int p = 97;
		int r = p + name.hashCode();
		r = p * r + (int) id;
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
