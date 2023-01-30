package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.DashboardView;
import com.restaurant.dto.RestaurantPeekHours;
import com.restaurant.repository.OrderRepo;

@RestController
public class DashBoardController {

	@Autowired
	private OrderRepo orderRepo;

	/**
	 * get Dashboard view of orders Service url: /rating method : GET
	 * 
	 * @param fromDate,toDate
	 * @return list of RatingDto {@link com.restaurant.dto.DashBoardDTO}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/dashboard")
	public DashboardView getDashboardView(@RequestParam String fromDate, @RequestParam String toDate) {
		return orderRepo.viewDashBoardByDated(fromDate, toDate);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/peekhours")
	public RestaurantPeekHours getRestaurantPeekHours() {
		return orderRepo.restaurantPeekHours();
	}

}
