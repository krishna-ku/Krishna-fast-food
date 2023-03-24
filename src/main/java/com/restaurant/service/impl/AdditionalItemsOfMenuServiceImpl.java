package com.restaurant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.entity.AdditionalItemsOfMenu;
import com.restaurant.repository.AdditionalItemsOfMenuRepo;

@Service
public class AdditionalItemsOfMenuServiceImpl {
	
	@Autowired
	private AdditionalItemsOfMenuRepo additionalItems;
	
	
	/**
	 * it will return all items of AdditionalItemsOfMenu
	 * @return List<AdditionalItemsOfMenu>
	 */
	public List<AdditionalItemsOfMenu> getAdditionalItemsOfMenu() {
		
		List<AdditionalItemsOfMenu> items = additionalItems.findAll();
		
		return items;
		
	}

}
