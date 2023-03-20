package com.restaurant.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingDTO<T> {

	private List<T> data=new ArrayList<>();
	private long totalElements;
	private int totalPages;
	

}
