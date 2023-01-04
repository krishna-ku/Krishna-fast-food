package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.dto.DashboardView;
import com.restaurant.dto.OrderStatistics;
import com.restaurant.entity.Order;

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

	@Query(nativeQuery = true, value = "SELECT (Date(orders.created_on)) date, COUNT(orders.id) count "
			+ "FROM orders WHERE orders.created_on between if(:fromDate IS NOT NULL,:fromDate-7,CURRENT_DATE-7) and if(:toDate IS NOT NULL,:toDate,CURRENT_DATE()) group by date(orders.created_on) order by date, orders.deleted=false")
	List<OrderStatistics> oneWeekOrders(String fromDate, String toDate);

}
