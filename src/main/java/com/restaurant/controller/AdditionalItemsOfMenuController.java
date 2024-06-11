package com.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.entity.AdditionalItemsOfMenu;
import com.restaurant.service.impl.AdditionalItemsOfMenuServiceImpl;

@RestController
@RequestMapping("additionalitems")
public class AdditionalItemsOfMenuController {
	
	@Autowired
	private AdditionalItemsOfMenuServiceImpl additionalItems;
	
	@GetMapping
	public ResponseEntity<List<AdditionalItemsOfMenu>> getAdditionalItems(){
		
		List<AdditionalItemsOfMenu> additional = additionalItems.getAdditionalItemsOfMenu();
		
		return new ResponseEntity<>(additional,HttpStatus.OK);
		
	}
	
	
	
	
}
