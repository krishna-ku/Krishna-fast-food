package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.impl.TokenService;

@RestController
@RequestMapping("/out")
public class TokenController {

	@Autowired
	private TokenService tokenService;

	/**
	 * remove token from map
	 * @param username
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Void> logoutUser(@RequestBody String username) {

		if (username != null) {
			tokenService.removeUserToken(username);
			return ResponseEntity.ok().build();
		}

		else {
			return ResponseEntity.badRequest().build();
		}
	}

}
