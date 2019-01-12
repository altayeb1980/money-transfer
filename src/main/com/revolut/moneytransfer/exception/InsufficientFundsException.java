package com.revolut.moneytransfer.exception;

import com.revolut.moneytransfer.model.Account;

public class InsufficientFundsException extends TransferFailureException {

    public InsufficientFundsException(Account account) {
        super("Insufficient funds: " + account);
    }

}
