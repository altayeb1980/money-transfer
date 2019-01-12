package com.revolut.moneytransfer.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.exception.AccountNotFoundException;
import com.revolut.moneytransfer.exception.TransferFailureException;
import com.revolut.moneytransfer.model.Account;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
	private AccountDAO accountDAO;

	public AccountResource(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	/**
	 * @return
	 * @throws TransferFailureException
	 */
	@GET
	@Path("/all")
	public List<Account> getAllAccounts() {
			return accountDAO.getAll();
	}

	/**
	 * 
	 * @param accountId
	 * @return
	 * @throws TransferFailureException
	 */
	@GET
	@Path("/{accountId}")
	public Account getAccount(@PathParam("accountId") long accountId){
			return accountDAO.findById(accountId).orElseThrow(()-> new AccountNotFoundException(accountId));
	}
}
