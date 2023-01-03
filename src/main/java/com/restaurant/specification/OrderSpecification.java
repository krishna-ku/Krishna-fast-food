package com.restaurant.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.dto.OrderDTO;

@Component
public class OrderSpecification {

	/**
	 * filter orders on certain conditions
	 * @param orderDTO
	 * @return
	 */
	public static Specification<OrderDTO> filterOrders(OrderDTO orderDTO) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (orderDTO.getOrderId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("orderId"), orderDTO.getOrderId()));

			if (orderDTO.getOrderStatus() != null)
				predicates.add(criteriaBuilder.equal(root.get("orderStatus"), orderDTO.getOrderStatus()));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

}
