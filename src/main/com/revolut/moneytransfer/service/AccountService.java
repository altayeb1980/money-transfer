package com.revolut.moneytransfer.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.revolut.moneytransfer.dao.DAOFactory;
import com.revolut.moneytransfer.exception.AccountDAOException;
import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.model.Account;


@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {

	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

	private static Logger log = Logger.getLogger(AccountService.class);
	
	
	
	/**
	 * @return
	 * @throws MoneyTransferException
	 */
    @GET
    @Path("/all")
    public List<Account> getAllAccounts() throws MoneyTransferException {
    	try {
        return daoFactory.getAccountDAO().getAllAccounts();
    	}catch(AccountDAOException exp) {
    		throw new MoneyTransferException(exp.getMessage());
    	}
    }
}
