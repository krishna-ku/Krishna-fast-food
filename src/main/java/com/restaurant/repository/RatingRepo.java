package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.dto.RatingDashBoardView;
import com.restaurant.entity.Rating;
import com.restaurant.entity.User;

public interface RatingRepo extends JpaRepository<Rating, Long> {
	
	User findByUser(User user);
	
	@Query(nativeQuery = true,value =  "select count(Rating.id) TotalUsersWhoGaveRating ,count(user.id) TotalUsers ,count(distinct order_id) RatedOrdersCount"
			+ " from restaurant.rating right join restaurant.user on rating.user_id=user.id")
	RatingDashBoardView viewRatingDashBoard();
	
	@Query("select r from Rating r where r.rating<=:ratingValue")//defalut value 5
//	@Query("select r from Rating r where r.rating like concat ('%',:ratingValue,'%')")
	List<Rating> ratingsByFilter(int ratingValue);
}
