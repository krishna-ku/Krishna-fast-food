package com.restaurant.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.restaurant.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This ExceptionHandler is handle MethodArgumentNotValidException class and
	 * throw our message to user
	 * 
	 * @param ex
	 * @return Custom Message
	 */

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> map = new HashMap<>();

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		map.put("errors", errors);

		return new ResponseEntity<>(map, headers, status);

	}

	/**
	 * This ExceptionHandler is handle our custom create exception
	 * {@link com.restaurant.exception.ResourceNotFoundException} which extends
	 * RunTimeException
	 * 
	 * @param ex
	 * @return apiResponse {@link com.restaurant.dto.ApiResponse}
	 */
	@ExceptionHandler(ResourceNotFoundException.class)

	public ResponseEntity<ApiResponse> resourceNotfoundExceptionhandler(ResourceNotFoundException ex) {
		String messsage = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(messsage, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BadRequestException.class) // use in orders

	public ResponseEntity<ApiResponse> handleBadRequest(BadRequestException ex) {
		String messsage = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(messsage, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NullRequestException.class)

	public ResponseEntity<ApiResponse> handleNullRequestException(NullRequestException ex) {
		String messsage = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(messsage, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
