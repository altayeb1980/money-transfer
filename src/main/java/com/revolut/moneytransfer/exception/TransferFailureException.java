package com.revolut.moneytransfer.exception;

public class TransferFailureException extends RuntimeException {
	
	public TransferFailureException() {
        super();
    }

	public TransferFailureException(String msg) {
		super(msg);
	}

	public TransferFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
