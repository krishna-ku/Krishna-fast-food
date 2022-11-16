package com.restaurant.exception;

public class NullRequestException extends RuntimeException {

	/**
	 * This in our custom created method which extends RunTimeException
	 */
	private static final long serialVersionUID = 1290687379605159538L;

	public NullRequestException(String message) {
		super(message);
	}

}
