package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.RatingDashBoardView;
import com.restaurant.repository.RatingRepo;

@RestController
@RequestMapping("/ratingdashboard")
public class RatingDashBoardController {

	@Autowired
	private RatingRepo ratingRepo;

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public RatingDashBoardView getRatingView() {
		return ratingRepo.viewRatingDashBoard();
	}

}
