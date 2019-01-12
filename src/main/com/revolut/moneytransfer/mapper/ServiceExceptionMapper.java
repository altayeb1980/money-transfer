package com.revolut.moneytransfer.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.revolut.moneytransfer.exception.ErrorResponse;
import com.revolut.moneytransfer.exception.TransferFailureException;



public class ServiceExceptionMapper implements ExceptionMapper<TransferFailureException>{


	public ServiceExceptionMapper() {
	}

	public Response toResponse(TransferFailureException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(exception.getMessage());

		// return internal server error
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}
}
