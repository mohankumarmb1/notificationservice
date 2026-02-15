package com.hrs.notificationservice.exceptions;

public class NotificationNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotificationNotFoundException() {
		super();
	}

	public NotificationNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
