package com.revolut.moneytransfer.exception;

public class UserNotFoundException extends TransferFailureException {

	public UserNotFoundException(String emailAddress) {
		super("User not found: " + emailAddress);
	}
}
