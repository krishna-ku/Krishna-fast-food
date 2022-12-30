package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	
	User findByEmail(String email);
	
	@Query("Select u from User u where u.email like concat('%',:email,'%') and u.firstName like concat('%',:firstName,'%')")
	List<User> findUsersByEmail(String email,String firstName);
}
