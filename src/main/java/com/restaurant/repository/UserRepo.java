package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
}
