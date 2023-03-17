package com.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.restaurant.dto.DashboardView;
import com.restaurant.dto.FilteredMenuItemDetail;
import com.restaurant.dto.OrderStatistics;
import com.restaurant.dto.RestaurantPeekHours;
import com.restaurant.entity.Order;
import com.restaurant.entity.User;
import com.restaurant.enums.OrderStatus;

public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

	@Query(nativeQuery = true, value = "select count(orders.id) totalOrders ,sum(if(status='COMPLETED',1,0)) as completedOrders,"
			+ "sum(if(status='WAITING',1,0)) as waitingOrders,sum(if(status='CANCELLED',1,0)) as cancelledOrders,"
			+ "count(distinct user.id) totalUsers " + "from orders inner join user on orders.user_id=user.id "
			+ "where  orders.created_on between :fromDate and :toDate and orders.deleted=false")
	DashboardView viewDashBoardByDated(String fromDate, String toDate);

	@Query(nativeQuery = true, value = "select coalesce(max(order_number),1000) as max_value from orders")
	Long findLastOrderNumber();

	@Query(nativeQuery = true, value = "SELECT (DATE(orders.created_on)) date,COUNT(orders.id) count FROM orders WHERE orders.created_on BETWEEN "
			+ ":fromDate and :toDate " + "GROUP BY DATE(orders.created_on) ORDER BY date, orders.deleted=false")
	List<OrderStatistics> oneWeekOrders(String fromDate, String toDate);

	@Query(nativeQuery = true, value = "select orders.id,orders.order_number,rating.rating from restaurant.orders inner join restaurant.rating on restaurant.orders.id=restaurant.rating.order_id")
	List<Object[]> filterOrdersBasedOnRatingAndPrice();

	@Query("select o from Order o where o.id = :orderId and o.user.email = :username and o.deleted = false")
	Optional<Order> findByIdAndUsername(long orderId, String username);

	// this method is for couponServiceImpl
	List<Order> findByUser(User user);

	@Query("select o from Order o where o.deleted=false")
	Page<Order> findAllNotDeletedOrders(Pageable pageable);

	@Query(nativeQuery = true, value = "select CONCAT(IF(HOUR(created_on) >= 12, HOUR(created_on) - 12, HOUR(created_on)), "
			+ "'-',IF(HOUR(created_on) >= 12, HOUR(created_on) - 11, HOUR(created_on) + 1)) as Time,count(*) Orders from orders "
			+ "where created_on between date_sub(Now(),interval 15 day) and Now() and Hour(created_on) between 10 and 21 "
			+ "group by Time order by Orders desc")
	List<RestaurantPeekHours> restaurantPeekHours();

//	@Query(nativeQuery = true, value = "select m.id as id, m.name as menuItem ,count(m.id) count from restaurant.orders "
//			+ "o inner join order_item oi on oi.order_id=o.id inner join menu m on oi.menu_id=m.id "
//			+ "where o.created_on between now()-interval 15 day and now() group by m.id,m.name order by count(m.id) desc limit 3")
	@Query(nativeQuery = true, value = "CALL get_last_fifteen_days_most_ordered_menu_items()")
	List<FilteredMenuItemDetail> getLastFifteenDaysMostOrderMenuItems();
	
	@Modifying
	@Query("update Order o set o.status = :newStatus where o.status = :currentStatus")
	int updateOrderStatus(OrderStatus currentStatus,OrderStatus newStatus);

}
