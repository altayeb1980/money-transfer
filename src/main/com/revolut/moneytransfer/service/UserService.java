package com.revolut.moneytransfer.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.revolut.moneytransfer.dao.DAOFactory;
import com.revolut.moneytransfer.exception.MoneyTransferException;
import com.revolut.moneytransfer.exception.UserDAOException;
import com.revolut.moneytransfer.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	
	private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
	
	@GET
    @Path("/all")
    public List<User> getAllUsers() throws MoneyTransferException {
		try {
		return daoFactory.getUserDAO().getAllUsers();
		}catch(UserDAOException exp) {
			throw new MoneyTransferException(exp.getMessage());
		}
    }
}
