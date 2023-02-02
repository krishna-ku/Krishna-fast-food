//package com.restaurant.service.impl;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.restaurant.dto.FilteredMenuItemDetail;
//import com.restaurant.entity.Order;
//import com.restaurant.entity.OrderItem;
//import com.restaurant.repository.OrderRepo;
//
//@Service
//public class DashBoardServiceImpl {
//
//	@Autowired
//	private OrderRepo orderRepo;
//
//	/**
//	 * get 3 dishes which order most in our restaurant in last 15 days
//	 * 
//	 * @return list of MostSoldDishesInLastFifteenDays
//	 */
//	public List<FilteredMenuItemDetail> getMostSoldDishes() {
//		List<FilteredMenuItemDetail> mostSoldMenuItemsInLastFifteenDays = new ArrayList<>();
//		List<Order> orders = orderRepo.getLastFifteenDaysOrders();
//		Map<String, Integer> dishCount = new HashMap<>();
//
//		for (Order order : orders) {
//			List<OrderItem> orderItems = order.getOrderItems();
//			for (OrderItem item : orderItems) {
//				dishCount.put(item.getMenu().getName(), dishCount.getOrDefault(item.getMenu().getName(), 0) + 1);
//			}
//		}
//
//		dishCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3)
//				.forEachOrdered(e -> mostSoldMenuItemsInLastFifteenDays
//						.add(new FilteredMenuItemDetail(e.getKey(), e.getValue())));
//
//		return mostSoldMenuItemsInLastFifteenDays;
//	}
//}
