package com.revolut.moneytransfer.exception;

public class InsufficientFundsException extends TransferFailureException {

    public InsufficientFundsException(String accountId) {
        super("Insufficient funds from account: " + accountId);
    }

}
