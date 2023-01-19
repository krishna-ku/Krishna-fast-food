package com.restaurant.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.restaurant.dto.MenuCategoryDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Menu_Category SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
public class MenuCategory extends BaseClass {

	private String name;

	public MenuCategory(MenuCategoryDTO menuCategoryDTO) {
		this.name = menuCategoryDTO.getName();
	}

}
