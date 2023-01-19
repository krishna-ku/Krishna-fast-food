package com.restaurant.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;

import com.restaurant.dto.RatingDTO;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;

//@Component
public class RatingSpecification {

	/**
	 * filter ratings on certain conditions
	 * 
	 * @param ratingDTO
	 * @return
	 */
	public static Specification<Rating> filterRatings(RatingDTO ratingDTO, String userName,
			Collection<? extends GrantedAuthority> authorities) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (ratingDTO.getId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("id"), ratingDTO.getId()));

			if (ratingDTO.getRating() > 0)
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), ratingDTO.getRating()));

			boolean isAdmin = authorities.stream().map(m -> m.getAuthority()).anyMatch(p -> p.equals("ROLE_ADMIN"));

			if (!isAdmin) {
				Join<Rating, User> join = root.join("user", JoinType.INNER);
				predicates.add(criteriaBuilder.equal(join.get("email"), userName));
			}

			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					ratingDTO.getDeleted() != null ? ratingDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		});

	}

}
