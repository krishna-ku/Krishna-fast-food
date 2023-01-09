package com.restaurant.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.dto.MenuDTO;
import com.restaurant.entity.Menu;

@Component
public class MenuSpecification {

	/**
	 * filter menus on certain conditions
	 * 
	 * @param menuDTO
	 * @return
	 */
	public static Specification<Menu> menuFilters(MenuDTO menuDTO) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (menuDTO.getName() != null) {
				predicates.add(criteriaBuilder.like(root.get("name"), "%" + menuDTO.getName() + "%"));
			}
			
//			if(menuDTO.getCategory()!=null) {
//				predicates.add(criteriaBuilder.like(root.get("category"), "%"+menuDTO.getCategory()+"%"));
//			}

			if (menuDTO.getPrice() > 0) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), menuDTO.getPrice()));
			}
			if (menuDTO.getId() > 0) {
				predicates.add(criteriaBuilder.equal(root.get("id"), menuDTO.getId()));
			}
			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					menuDTO.getDeleted() != null ? menuDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}
}
