package com.restaurant.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * This in our custom created method which extends RunTimeException
	 */
	private static final long serialVersionUID = 1290687379605159538L;
	private final String resouceName;
	private final String fieldName;
	private final long fieldValue;

	public ResourceNotFoundException(String resouceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resouceName, fieldName, fieldValue));
		this.resouceName = resouceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
