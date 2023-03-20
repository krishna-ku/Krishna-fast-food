package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.DashboardView;
import com.restaurant.dto.FilteredMenuItemDetail;
import com.restaurant.dto.RestaurantPeekHours;
import com.restaurant.repository.OrderRepo;

@RestController
public class DashBoardController {

	@Autowired
	private OrderRepo orderRepo;

//	@Autowired
//	private DashBoardServiceImpl dashBoardServiceImpl;

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

	/**
	 * Know Restaurant Peek Hours on the basis of last 15 days orders
	 * 
	 * @return list<RestaurantPeekHours>
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/peekhours")
	public List<RestaurantPeekHours> getRestaurantPeekHours() {
		return orderRepo.restaurantPeekHours();
	}

	/**
	 * get 3 dishes which order most in our restaurant in last 15 days
	 * 
	 * @return List<MostSoldDishesInLastFifteenDays>
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/mostorderdish")
	public List<FilteredMenuItemDetail> getMostOrderDish() {
		return orderRepo.getLastFifteenDaysMostOrderMenuItems();
	}

}
