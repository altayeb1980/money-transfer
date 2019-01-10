package com.revolut.moneytransfer.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.moneytransfer.dao.DAOFactory;
import com.revolut.moneytransfer.exception.AccountDAOException;
import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.model.UserTransaction;
import com.revolut.moneytransfer.utils.MoneyUtil;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {

	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);

	/**
	 * Transfer fund between two accounts.
	 * 
	 * @param transaction
	 * @return
	 * @throws CustomException
	 */
	@POST
	public Response transferFund(UserTransaction transaction) throws MoneyTransferException {

		String currency = transaction.getCurrencyCode();

		if (!MoneyUtil.INSTANCE.validateCcyCode(currency)) {
			throw new WebApplicationException("Currency Code Invalid ", Response.Status.BAD_REQUEST);
		}

		try {
			int updateCount = daoFactory.getAccountDAO().transferAccountBalance(transaction);
			if (updateCount == 2) {
				return Response.status(Response.Status.OK).build();
			} else {
				// transaction failed
				throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);

			}
		} catch (AccountDAOException exp) {
			throw new MoneyTransferException(exp.getMessage());
		}
	}
}
