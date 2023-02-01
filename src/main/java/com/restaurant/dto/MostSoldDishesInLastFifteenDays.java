package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class MostSoldDishesInLastFifteenDays {
	private String dishName;
	private int count;
}