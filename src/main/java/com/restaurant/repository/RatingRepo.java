package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.entity.Rating;
import com.restaurant.entity.User;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	
	User findByUser(User user);
	
	@Query("select r from Rating r where r.rating<=:ratingValue")
	List<Rating> ratingsByFilter(int ratingValue);
	
}
