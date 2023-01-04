package com.restaurant.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public String getString() {
		return "api is working fine";
	}

}
