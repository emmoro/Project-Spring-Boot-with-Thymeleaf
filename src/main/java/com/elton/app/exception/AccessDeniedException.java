package com.elton.app.exception;

public class AccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4546825908392102821L;

	public AccessDeniedException(String message) {
		super(message);
	}
	
}
