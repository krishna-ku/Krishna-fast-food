package com.restaurant.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.restaurant.dto.OrderDTO;
import com.restaurant.entity.Order;
import com.restaurant.entity.User;

@Component
public class OrderSpecification {

	/**
	 * filter orders on certain conditions
	 * 
	 * @param orderDTO
	 * @return
	 */
	public static Specification<Order> filterOrders(OrderDTO orderDTO, String userName,
			Collection<? extends GrantedAuthority> authorities) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (orderDTO.getOrderId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("orderId"), orderDTO.getOrderId()));

			if (orderDTO.getOrderStatus() != null)
				predicates.add(criteriaBuilder.equal(root.get("orderStatus"), orderDTO.getOrderStatus()));

			boolean isAdmin = authorities.stream().map(o -> o.getAuthority()).anyMatch(m -> m.equals("ROLE_ADMIN"));

			if (!isAdmin) {
				Join<Order, User> join = root.join("user", JoinType.INNER);
				predicates.add(criteriaBuilder.equal(join.get("email"), userName));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

}
