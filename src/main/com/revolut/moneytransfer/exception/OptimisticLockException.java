package com.revolut.moneytransfer.exception;

import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;

public class OptimisticLockException extends TransferFailureException {

    public OptimisticLockException(Account account) {
        super("Optimistic lock exception: " + account);
    }

}
