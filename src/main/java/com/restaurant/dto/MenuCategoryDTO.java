package com.restaurant.dto;

import com.restaurant.entity.MenuCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuCategoryDTO {

	private long id;

	private String name;

	private Boolean deleted;

	public MenuCategoryDTO(MenuCategory menuCategory) {
		this.id = menuCategory.getId();
		this.name = menuCategory.getName();
		this.deleted = menuCategory.isDeleted();
	}

}
