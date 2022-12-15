package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;

import com.restaurant.dto.DashboardView;
import com.restaurant.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query(nativeQuery = true, value = "select count(orders.id) totalOrders ,sum(if(status='COMPLETED',1,0)) as completedOrders,"
			+ "sum(if(status='WAITING',1,0)) as waitingOrders,sum(if(status='CANCELLED',1,0)) as cancelledOrders,"
			+ "count(distinct user.id) totalUsers " + "from orders inner join user on orders.user_id=user.id "
			+ "where  orders.created_on between :fromDate and :toDate and orders.deleted=false")
	DashboardView viewDashBoardByDated(String fromDate,String toDate);

//	@Query("select o from Order o where o.deleted=:deleted")
//	List<Order> findOrdersByDeleted(@Param("deleted") boolean d);

}
