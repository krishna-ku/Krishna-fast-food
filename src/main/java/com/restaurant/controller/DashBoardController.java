package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.DashboardView;
import com.restaurant.repository.OrderRepo;

@RestController
@RequestMapping("/dashboards")
public class DashBoardController {

	@Autowired
	private OrderRepo orderRepo;

	@GetMapping
	public DashboardView getDashboardView(@RequestBody String fromDate,@RequestBody String toDate) {
		return orderRepo.viewDashBoardByDated(fromDate, toDate);
	}

}
