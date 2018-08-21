package com.bridgelabz.eurekaUserService.exception;

/**
 * @author Chaitra Ankolekar
 * Purpose :UserExceptionHandler
 */
public class UserExceptionHandler extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public UserExceptionHandler(String message) {

		super(message);

	}

}