package com.revolut.moneytransfer.service;

import javax.validation.constraints.NotNull;

import com.revolut.moneytransfer.model.UserTransaction;

public interface TransferService {

	void transferFund(@NotNull UserTransaction userTransaction);
}
