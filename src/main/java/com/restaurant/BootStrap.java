////package com.restaurant;
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.event.ContextRefreshedEvent;
////import org.springframework.context.event.EventListener;
////import org.springframework.stereotype.Component;
////
////import com.restaurant.dto.DashboardView;
////import com.restaurant.repository.OrderRepo;
////
////@Component
////public class BootStrap {
////
////	@Autowired
////	private OrderRepo orderRepo;
////
////	@EventListener(ContextRefreshedEvent.class)
////	public void refreshEvent() {
////		DashboardView findOrders = orderRepo.findOrdersByDate("2022-12-13", "2022-12-15");
////		System.out.println(findOrders);
////	}
////}
//Menu
//item 1  10  1000 order items rating average
//item 2  12  3
//item 3  15  3.5
//
//Order  -> rating
// -> orderitem -> rating
//
//
//case overall rating :
//    1 order : 3
//        10 orderitems: 3
//case particular rating:
//    1 order : avg (orderitems rating)
//        10 orderitems :1,2,3...
//
