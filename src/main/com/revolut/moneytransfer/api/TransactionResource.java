package com.revolut.moneytransfer.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.moneytransfer.exception.TransferFailureException;
import com.revolut.moneytransfer.model.UserTransaction;
import com.revolut.moneytransfer.service.TransferService;

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
		try {
			transferService.transferFund(transaction);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			System.err.println("error in transferFun "+e);
			throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
		}
	}
}
