package com.dnyanu.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5717195705526363522L;

	public ResourceNotFoundException() {
		super("Resource not found on server !! ");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
