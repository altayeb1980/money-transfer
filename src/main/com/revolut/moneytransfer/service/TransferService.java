package com.revolut.moneytransfer.service;

import java.util.concurrent.CompletableFuture;

import javax.validation.constraints.NotNull;

import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;

public interface TransferService {

	CompletableFuture<Account> transferFund(@NotNull UserTransaction userTransaction);
}
