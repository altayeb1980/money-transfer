package com.revolut.moneytransfer.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.moneytransfer.exception.ErrorResponse;
import com.revolut.moneytransfer.exception.MoneyTransferException;



@Provider
public class ServiceExceptionMapper implements ExceptionMapper<MoneyTransferException>{


	public ServiceExceptionMapper() {
	}

	public Response toResponse(MoneyTransferException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(exception.getMessage());

		// return internal server error for DAO exceptions
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}
}
