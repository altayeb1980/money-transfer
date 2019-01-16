package com.revolut.moneytransfer.service;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.exception.AccountNotFoundException;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;


public class SimpleTransferService implements TransferService {
	private AccountDAO accountDAO;
	private final TransferServiceValidator transferServiceValidator;
	
	public SimpleTransferService(final AccountDAO accountDAO, final TransferServiceValidator transferServiceValidator) {
		this.accountDAO = accountDAO;
		this.transferServiceValidator = transferServiceValidator;
	}

	private Account findAccount(@NotNull long accountId) {
		return accountDAO.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	
	
	public void transferFund(final UserTransaction userTransaction) {
		transferServiceValidator.validateCcyCode(userTransaction.getCurrencyCode());
		Account debitorAccount = findAccount(userTransaction.getFromAccountId());
		Account creditorAccount = findAccount(userTransaction.getToAccountId());
		
		transferServiceValidator.validateTransactionCurrency(debitorAccount.getUserName(), debitorAccount.getCurrencyCode(), userTransaction.getCurrencyCode());
		transferServiceValidator.validateTransactionCurrency(creditorAccount.getUserName(), creditorAccount.getCurrencyCode(), userTransaction.getCurrencyCode());
		BigDecimal debitorAccountLeftOver = debitorAccount.getBalance().subtract(userTransaction.getAmount());
		transferServiceValidator.validateInsufficientFunds(debitorAccountLeftOver, String.valueOf(debitorAccount.getAccountId()));
		
		Account newDebitorAccount = new Account(userTransaction.getFromAccountId(), debitorAccount.getUserName(), debitorAccountLeftOver,
				debitorAccount.getCurrencyCode());
		accountDAO.updateAccountBalance(newDebitorAccount);
		
		
		Account newCreditorAccount = new Account(userTransaction.getToAccountId(), creditorAccount.getUserName(),
				creditorAccount.getBalance().add(userTransaction.getAmount()), creditorAccount.getCurrencyCode());
		accountDAO.updateAccountBalance(newCreditorAccount);
		
	}	
}
