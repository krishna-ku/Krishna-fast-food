package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Rating;
import com.restaurant.entity.User;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	
	User findByUser(User user);

}
