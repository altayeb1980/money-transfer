package com.revolut.moneytransfer.dao;

import java.util.List;

import com.revolut.moneytransfer.exception.AccountDAOException;
import com.revolut.moneytransfer.model.Account;

public interface AccountDAO {

	List<Account> getAllAccounts() throws AccountDAOException;
}
