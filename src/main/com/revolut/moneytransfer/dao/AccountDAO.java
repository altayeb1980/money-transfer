package com.revolut.moneytransfer.dao;

import java.util.List;

import com.revolut.moneytransfer.exception.AccountDAOException;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;

public interface AccountDAO {

	List<Account> getAllAccounts() throws AccountDAOException;
	Account getAccountById(long accountId) throws AccountDAOException;
	int transferAccountBalance(UserTransaction userTransaction) throws AccountDAOException;
}
