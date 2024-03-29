package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.RestaurantDTO;
import com.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	/**
	 * Add Restaurant service url : /restaurant method : Post
	 * 
	 * @param RestaurantDTO
	 * @return RestaurantDto
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PostMapping
	public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDto) {
		RestaurantDTO restaurant = restaurantService.createRestaurant(restaurantDto);
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	/**
	 * Update Restaurant by id service url: /restaurant/restaurantId method : PUT
	 * 
	 * @param restaurantId
	 * @param restaurantDto
	 * @return Updated restaurantDto {@link com.restaurant.dto.restaurantDto}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping
	public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO restaurantDto) {
		return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantDto));
	}

	/**
	 * Delete Restaurant by id Method : DELETE Service url: /restaurant/restaurantId
	 * 
	 * @param restaurantId
	 * 
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable long restaurantId) {
		restaurantService.deleteRestaurant(restaurantId);
		return new ResponseEntity<>(new ApiResponse("Restaurant Deleted Successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of restaurants Service url: /restaurant method : GET
	 * 
	 * @return list of Restaurants {@link com.restaurant.entity.Restaurant}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<RestaurantDTO> getRestaurantDetails() {
		return ResponseEntity.ok(restaurantService.getRestaurantDetails());
	}

	/**
	 * get details of user isDelted or notDeleted service url :/restaurant/
	 * 
	 * @param isDeleted=true or false
	 * @return list of restaurants
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filterrestaurants")
	public ResponseEntity<List<RestaurantDTO>> findAll(
			@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
		List<RestaurantDTO> restaurantDtos = restaurantService.findAllFilter(isDeleted);
		return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
	}

}
