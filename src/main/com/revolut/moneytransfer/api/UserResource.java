package com.revolut.moneytransfer.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import com.google.inject.Inject;
import com.revolut.moneytransfer.dao.UserDAO;
import com.revolut.moneytransfer.exception.UserNotFoundException;
import com.revolut.moneytransfer.model.User;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	
	private final UserDAO userDAO;
	
	public UserResource(final UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	
	@GET
    @Path("/all")
    public List<User> getAllUsers() {
		return userDAO.findAll();
    }
	
	
	@GET
	@Path("/{emailAddress}")
	public User getUser(@PathParam("emailAddress") String emailAddress) {
			return userDAO.findByEmailAddress(emailAddress).orElseThrow(() -> new UserNotFoundException(emailAddress));
	}
}
