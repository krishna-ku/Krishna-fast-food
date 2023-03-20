package com.restaurant.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;

import com.restaurant.dto.UserDTO;
import com.restaurant.entity.User;

//@Component
public class UserSpecification {

	/**
	 * filter users on certain conditions
	 * 
	 * @param userDTO
	 * @return
	 */
	public static Specification<User> filterUsers(UserDTO userDTO, String userName,Collection<? extends GrantedAuthority> authorities) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (userDTO.getFirstName() != null)
				predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + userDTO.getFirstName() + "%"));

			if (userDTO.getEmail() != null)
				predicates.add(criteriaBuilder.equal(root.get("email"), "%" + userDTO.getEmail() + "%"));

			if (userDTO.getId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("id"), userDTO.getId()));

			// if(userName!=null && "USER".equals(userDTO.getRole())) {
//			predicates.add(criteriaBuilder.equal(root.get("email"), userName));
			//
			// }
			// else if("ADMIN".equals(userDTO.getRole()) ||
			// "MANAGER".equals(userDTO.getRole())) {
			// }

			// predicates.add(criteriaBuilder
			// .equal(criteriaBuilder.selectCase().when(criteriaBuilder.equal(root.get("email"),
			// userName), "USER")
//				.otherwise("ADMIN").as(String.class), "USER"));

			// Join<User, User> join=root.join("role",JoinType.INNER);
			// predicates.add(criteriaBuilder.equal(join.get("email"), userName));
			// predicates.add(criteriaBuilder.equal(join.get("role"), "USER"));
			
			boolean isAdmin = authorities.stream().map(o->o.getAuthority()).anyMatch(p->p.equals("ROLE_ADMIN"));

			if (!isAdmin)
				predicates.add(criteriaBuilder.equal(root.get("email"), userName));

			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					userDTO.getDeleted() != null ? userDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
	}

}
