package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping
	public ResponseEntity<List<RatingDTO>> getAllRatings() {
		List<RatingDTO> ratingDto = ratingService.getAllRatings();
		return new ResponseEntity<>(ratingDto, HttpStatus.OK);
	}
	
	/**
	 * get list of Rating Service url: /rating method : GET
	 * 
	 * @return list of RatingDto {@link com.restaurant.dto.RatingDTO}
	 */
	@GetMapping("filter")
	public ResponseEntity<List<RatingDTO>> getAllRatings(@RequestParam(defaultValue = "3") int ratingValue) {
		List<RatingDTO> ratingDto = ratingService.ratingsByFilter(ratingValue);
		return new ResponseEntity<>(ratingDto, HttpStatus.OK);
	}

	/**
	 * get detail of Rating by id Service url: /rating/id method: GET
	 * 
	 * @param id
	 * @return RatingDto of particular id
	 * @see com.restaurant.dto.RatingDTO
	 */
	@GetMapping("/{ratingId}")
	public ResponseEntity<RatingDTO> getRatingById(@PathVariable long ratingId) {

		return ResponseEntity.ok(ratingService.getRatingById(ratingId));
	}

	/**
	 * get details of user isDelted or notDeleted service url :/user/
	 * 
	 * @param isDeleted=true or false
	 * @return list of users
	 */
	@GetMapping("/filterratings")
	public ResponseEntity<List<RatingDTO>> findAll(
			@RequestParam(value = "isDeleted", required = false, defaultValue = "false") boolean isDeleted) {
		List<RatingDTO> ratingDtos = ratingService.findAllFilter(isDeleted);
		return new ResponseEntity<>(ratingDtos, HttpStatus.OK);
	}

}
