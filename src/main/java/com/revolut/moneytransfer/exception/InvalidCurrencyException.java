package com.revolut.moneytransfer.exception;

public class InvalidCurrencyException extends TransferFailureException{
	
	public InvalidCurrencyException(String username) {
        super("invalid transaction currency from username " + username);
    }
}
