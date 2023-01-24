package com.restaurant.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.restaurant.entity.User;

public class CouponSpecification {

	/**
	 * filter user on the basis of date and orders
	 * 
	 * @param orderNumber
	 * @param date
	 * @return
	 */
	public static Specification<User> generateCouponBasisOfDateAndOrders(int orderNumber, Date date) {

		return ((root, criteriaQuery, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(root.get("orderNumber"), orderNumber));

			predicates.add(criteriaBuilder.equal(root.get("date"), date));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		});

	}

}
