package com.revolut.moneytransfer.exception;

public class AccountDAOException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public AccountDAOException(String msg) {
		super(msg);
	}

	public AccountDAOException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
