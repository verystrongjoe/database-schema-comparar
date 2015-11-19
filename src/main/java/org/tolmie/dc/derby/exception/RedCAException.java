package org.tolmie.dc.derby.exception;

public class RedCAException extends Exception {

	RedCAInStatus status;
	
	public RedCAException(String message, Exception cause, RedCAInStatus status) {
		
		super(message, cause);
		this.status = status;
	}

	
	public RedCAException(String message, Exception cause) {
		
		super(message, cause);
	}

	/**
	 * Create an AccessException with a specific message.
	 * @param message the message
	 */
	public RedCAException(String message) {
		
		super(message);
	}
	
}
