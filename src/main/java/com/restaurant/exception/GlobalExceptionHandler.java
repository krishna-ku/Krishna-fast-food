package com.restaurant.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
//		Map<String, String> resp = new HashMap<>();
//
//		ex.getBindingResult().getAllErrors().forEach(error -> {
//
//			String fieldName = ((FieldError) error).getField();
//
//			String message = error.getDefaultMessage();
//
//			resp.put(fieldName, message);
//
//		});
//
//		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
//	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, Object> map=new HashMap<>();
		
		List<String> errors=ex.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
		
		map.put("errors", errors);
		
		return new ResponseEntity<>(map,headers,status);
		
	}

//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//
////		Map<String, String> map=new HashMap<>();
//
////		ex.getBindingResult().getAllErrors.forEach(error->{
////			
////			String fieldName= ((FieldError)error).getField();
////			
////			String message= error.getDefaultMessage();
////			
////			map.put(fieldName, message);
////		});
//		
//		String message=ex.getCause().getMessage();
//		ApiResponse apiResponse = new ApiResponse(message, false);
//		return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
//		
//	}

//	  @ExceptionHandler(DataIntegrityViolationException.class)
//	    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
//	                                                                  WebRequest request) {
//	        if (ex.getCause() instanceof ConstraintViolationException) {
//	            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
//	        }
//	        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
//	    }

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

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(BadRequestException.class)

	public ResponseEntity<ApiResponse> handleBadRequest(BadRequestException ex) {
		String messsage = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(messsage, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}
	
	

}
