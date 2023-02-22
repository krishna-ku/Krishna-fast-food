package com.restaurant.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	public static Map<String, String> tokeMap = new ConcurrentHashMap<>();

	/**
	 * remove token from Map
	 * 
	 * @param username
	 */
	public void removeUserToken(String username) {
		tokeMap.remove(username);
	}

}
