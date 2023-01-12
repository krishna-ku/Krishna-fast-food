package com.restaurant.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.dto.MenuCategoryDTO;
import com.restaurant.entity.MenuCategory;

@Component
public class MenuCategorySpecification {

	/**
	 * filter menu category on the basis of category name and deleted
	 * @param menuCategoryDTO
	 * @return
	 */
	public static Specification<MenuCategory> filterMenuCategories(MenuCategoryDTO menuCategoryDTO) {

		return ((root, criteriaQuery, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();
			
			if(menuCategoryDTO.getId()>0)
				predicates.add(criteriaBuilder.equal(root.get("id"), menuCategoryDTO.getId()));

			if (menuCategoryDTO.getName() != null)
				predicates.add(criteriaBuilder.like(root.get("name"), "%" + menuCategoryDTO.getName() + "%"));

			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					menuCategoryDTO.getDeleted() != null ? menuCategoryDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		});
	}

}
