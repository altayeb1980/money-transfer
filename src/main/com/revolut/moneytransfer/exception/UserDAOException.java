package com.revolut.moneytransfer.exception;

public class UserDAOException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public UserDAOException(String msg) {
		super(msg);
	}

	public UserDAOException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
