//package com.restaurant;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import com.restaurant.dto.DashboardView;
//import com.restaurant.repository.OrderRepo;
//
//@Component
//public class BootStrap {
//
//	@Autowired
//	private OrderRepo orderRepo;
//	
//	@EventListener(ContextRefreshedEvent.class)
//	public void refreshEvent() {
//		DashboardView findOrders = orderRepo.findOrdersByDate("2022-12-13", "2022-12-15");
//		System.out.println(findOrders);
//	}
//}
