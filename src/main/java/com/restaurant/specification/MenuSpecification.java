package com.restaurant.specification;

import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.entity.Menu;

@Component
public class MenuSpecification {

	public static Specification<Menu> menuIsDeletedOrNot(Menu menu) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			if (menu.getName() != null) {
				Path<String> namePath = root.get("name");
				return criteriaBuilder.equal(namePath, menu.getName());
			}

			else if (Float.valueOf(menu.getPrice()) != null) {
				Path<Float> price = root.get("price");
				return criteriaBuilder.lessThanOrEqualTo(price, menu.getPrice());
			}

			return null;
		});
	}
//	public static Specification<User> userIsDeletedOrNot(){
//		return new Specification<User>() {
//			public Predicate toPredicate(Root<T> root,CriteriaQuery query,CriteriaBuilder cb) {
//				return cb.equal(root.get(USER_EXCEPTION.), root)
//			}
//		};
//	}
}
