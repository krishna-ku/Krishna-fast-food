package com.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.dto.DashboardView;
import com.restaurant.dto.OrderStatistics;
import com.restaurant.dto.RestaurantPeekHours;
import com.restaurant.entity.Order;
import com.restaurant.entity.User;

public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

	@Query(nativeQuery = true, value = "select count(orders.id) totalOrders ,sum(if(status='COMPLETED',1,0)) as completedOrders,"
			+ "sum(if(status='WAITING',1,0)) as waitingOrders,sum(if(status='CANCELLED',1,0)) as cancelledOrders,"
			+ "count(distinct user.id) totalUsers " + "from orders inner join user on orders.user_id=user.id "
			+ "where  orders.created_on between :fromDate and :toDate and orders.deleted=false")
	DashboardView viewDashBoardByDated(String fromDate, String toDate);

//	@Query("select o from Order o where o.deleted=:deleted")
//	List<Order> findOrdersByDeleted(@Param("deleted") boolean d);

	@Query(nativeQuery = true, value = "select coalesce(max(order_number),1000) as max_value from orders")
	Long findLastOrderNumber();

	@Query(nativeQuery = true, value = "SELECT (DATE(orders.created_on)) date,COUNT(orders.id) count FROM orders WHERE orders.created_on BETWEEN "
			+ ":fromDate and :toDate " + "GROUP BY DATE(orders.created_on) ORDER BY date, orders.deleted=false")
	List<OrderStatistics> oneWeekOrders(String fromDate, String toDate);

	@Query(nativeQuery = true, value = "select orders.id,orders.order_number,rating.rating from restaurant.orders inner join restaurant.rating on restaurant.orders.id=restaurant.rating.order_id")
	List<Object[]> filterOrdersBasedOnRatingAndPrice();

	@Query("select o from Order o where o.id = :orderId and o.user.email = :username and o.deleted = false")
	Optional<Order> findByIdAndUsername(long orderId, String username);

	List<Order> findByUser(User user);

	@Query(nativeQuery = true, value = "SELECT HOUR(created_on) as Time,COUNT(*) as Orders FROM orders GROUP BY HOUR(created_on) ORDER BY Orders DESC LIMIT 1")
	RestaurantPeekHours restaurantPeekHours();

}
