package com.revolut.moneytransfer.exception;

public class AccountNotFoundException extends TransferFailureException {

	public AccountNotFoundException(long accountId) {
		super("Account not found: " + accountId);
	}
}
