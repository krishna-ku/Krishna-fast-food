package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.dto.RatingDashBoardView;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	
	User findByUser(User user);
	
	@Query(nativeQuery = true,value =  "select count(Rating.id) TotalUsersWhoseGivesRating ,count(user.id) TotalUsersInDB ,count(distinct order_id) DistinctOrderId"
			+ " from restaurant.rating right join restaurant.user on rating.user_id=user.id")
	RatingDashBoardView viewRatingDashBoard();
	
	@Query("select r from Rating r where r.rating<=:ratingValue")
	List<Rating> ratingsByFilter(int ratingValue);
}
