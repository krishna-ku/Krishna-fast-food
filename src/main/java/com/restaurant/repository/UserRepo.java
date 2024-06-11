package com.restaurant.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.entity.User;

public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findByEmail(String email);

	@Query("Select u from User u where u.email like concat('%',:email,'%') and u.firstName like concat('%',:firstName,'%')")
	List<User> findUsersByEmail(String email, String firstName);

	@Query("Select u from User u where u.deleted=false")
	Page<User> findAllNotDeletedUsers(Pageable pageable);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.deleted = true WHERE u.id IN (:userList)")
	void deleteUserById(List<Long> userList);
	
	@Modifying
	@Query("update User u set u.deleted=false where u.id IN (:usersList)")
	void activateUsersById(List<Long> usersList);
}
