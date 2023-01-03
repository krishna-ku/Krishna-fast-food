package com.restaurant.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.dto.UserDTO;

@Component
public class UserSpecification {

	/**
	 * filter users on certain conditions
	 * @param userDTO
	 * @return
	 */
	public static Specification<UserDTO> filterUsers(UserDTO userDTO) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (userDTO.getFirstName() != null)
				predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + userDTO.getFirstName() + "%"));

			if (userDTO.getEmail() != null)
				predicates.add(criteriaBuilder.equal(root.get("email"), "%" + userDTO.getEmail() + "%"));

			if (userDTO.getId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("id"), userDTO.getId()));

			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					userDTO.getDeleted() != null ? userDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

}
