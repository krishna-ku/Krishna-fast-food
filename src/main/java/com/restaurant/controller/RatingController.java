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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.RatingDTO;
import com.restaurant.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	/**
	 * Add Rating service url : /rating method : Post
	 * 
	 * @param RatingDTO
	 * @return RatingDto {@link com.restaurant.dto.RatingDTO}
	 */
	@PostMapping("/{orderId}/user/{userId}")
	public ResponseEntity<RatingDTO> createRating(@Valid @RequestBody RatingDTO ratingDto, @PathVariable long orderId,
			@PathVariable long userId) {

		RatingDTO rating = ratingService.createRating(ratingDto, orderId, userId);

		return new ResponseEntity<>(rating, HttpStatus.CREATED);
	}

	/**
	 * Update Rating by id service url: /rating/id method : PUT
	 * 
	 * @param id
	 * @param RatingDto
	 * @return Updated RatingDto {@link com.restaurant.dto.RatingDto}
	 */
//	@PutMapping("/{id}")
//	public ResponseEntity<RatingDto> updateRating(@RequestBody RatingDto ratingDto, @PathVariable long id) {
//		RatingDto updateRating = this.ratingService.updatRating(ratingDto, id);
//		return new ResponseEntity<>(updateRating, HttpStatus.OK);
//	}

	/**
	 * Delete Rating by id Method : DELETE Service url: /rating/id
	 * 
	 * @param id
	 * 
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@DeleteMapping("/{ratingId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long ratingId) {
		this.ratingService.deleteRating(ratingId);
		return new ResponseEntity<>(new ApiResponse("Rating delete successfully", true), HttpStatus.OK);
	}

	/**
	 * get list of Rating Service url: /rating method : GET
	 * 
	 * @return list of RatingDto {@link com.restaurant.dto.RatingDTO}
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping
	public ResponseEntity<List<RatingDTO>> getAllRatings() {
		List<RatingDTO> ratingDto = ratingService.getAllRatings();
		return new ResponseEntity<>(ratingDto, HttpStatus.OK);
	}

	/**
	 * filter ratings on basis of id,rating and deleted
	 * 
	 * @param menuDTO
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@GetMapping("/filter")
	public ResponseEntity<List<RatingDTO>> filterRatings(@RequestBody RatingDTO ratingDTO) {
		List<RatingDTO> filterRatings = ratingService.filterRatings(ratingDTO);
		return new ResponseEntity<>(filterRatings, HttpStatus.OK);
	}

	/**
	 * Activate Rating
	 * 
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
	@PutMapping("/activate/{ratingId}")
	public ResponseEntity<String> activateUserEntity(@PathVariable long ratingId) {
		return ResponseEntity.ok(ratingService.activateRating(ratingId));
	}

}
