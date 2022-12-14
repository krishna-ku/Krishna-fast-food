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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ApiResponse;
import com.restaurant.dto.RatingDto;
import com.restaurant.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	/**
	 * Add Rating
	 * service url : /rating
	 * method : Post
	 * @param RatingDto
	 * @return RatingDto {@link com.restaurant.dto.RatingDto}
	 */
	@PostMapping("/{orderId}/user/{userId}")
	public ResponseEntity<RatingDto> createRating(@Valid @RequestBody RatingDto ratingDto, @PathVariable long orderId,@PathVariable long userId) {

		RatingDto rating = ratingService.createRating(ratingDto,orderId,userId);

		return new ResponseEntity<>(rating ,HttpStatus.CREATED);
	}

	/**
	 * Update Rating by id
	 * service url: /rating/id
	 * method : PUT
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
	 * Delete Rating by id
	 * Method : DELETE
	 * Service url: /rating/id
	 * @param id
	 * 
	 */
	@DeleteMapping("/{ratingId}")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long ratingId){
		this.ratingService.deleteRating(ratingId);
		return new ResponseEntity<>(new ApiResponse("Rating delete successfully",true),HttpStatus.OK);
	}

	/**
	 * get list of Rating
	 * Service url: /rating
	 * method :  GET
	 * @return list of RatingDto {@link com.restaurant.dto.RatingDto}
	 */
	@GetMapping
	public ResponseEntity<List<RatingDto>> getAllRatings() {
		List<RatingDto> ratingDto = ratingService.getAllRatings();
		return new ResponseEntity<>(ratingDto, HttpStatus.OK);
	}

	/**
	 * get detail of Rating by id
	 * Service url: /rating/id
	 * method: GET
	 *@param id
	 * @return RatingDto of particular id
	 * @see com.restaurant.dto.RatingDto
	 */
	@GetMapping("/{ratingId}")
	public ResponseEntity<RatingDto> getRatingById(@PathVariable long ratingId) {

		return ResponseEntity.ok(ratingService.getRatingById(ratingId));
	}

}
