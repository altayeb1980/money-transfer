package com.revolut.moneytransfer.api;

import java.util.concurrent.ExecutionException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.moneytransfer.exception.TransferFailureException;
import com.revolut.moneytransfer.model.Account;
import com.revolut.moneytransfer.model.UserTransaction;
import com.revolut.moneytransfer.service.TransferService;
import com.revolut.moneytransfer.utils.MoneyUtil;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

	private TransferService transferService;

	public TransactionResource(TransferService transferService) {
		this.transferService = transferService;
	}

	/**
	 * Transfer fund between two accounts.
	 * 
	 * @param transaction
	 * @return
	 * @throws TransferFailureException
	 */
	@POST
	public Response transferFund(UserTransaction transaction) throws TransferFailureException {

		String currency = transaction.getCurrencyCode();

		if (!MoneyUtil.INSTANCE.validateCcyCode(currency)) {
			throw new WebApplicationException("Currency Code Invalid ", Response.Status.BAD_REQUEST);
		}

		try {
			Account account = transferService.transferFund(transaction).get();
			if (account != null) {
				return Response.status(Response.Status.OK).build();
			}else {
				throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
		}
	}
}
