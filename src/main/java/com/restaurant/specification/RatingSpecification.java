package com.restaurant.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.restaurant.dto.RatingDTO;

@Component
public class RatingSpecification {

	/**
	 * filter ratings on certain conditions
	 * 
	 * @param ratingDTO
	 * @return
	 */
	public static Specification<RatingDTO> filterRatings(RatingDTO ratingDTO) {

		return ((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (ratingDTO.getId() > 0)
				predicates.add(criteriaBuilder.equal(root.get("id"), ratingDTO.getId()));

			if (ratingDTO.getRating() > 0)
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), ratingDTO.getRating()));

			predicates.add(criteriaBuilder.equal(root.get("deleted"),
					ratingDTO.getDeleted() != null ? ratingDTO.getDeleted() : false));

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

		});

	}

}
