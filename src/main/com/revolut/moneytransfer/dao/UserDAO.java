package com.revolut.moneytransfer.dao;

import java.util.List;

import com.revolut.moneytransfer.exception.UserDAOException;
import com.revolut.moneytransfer.model.User;

public interface UserDAO {
	List<User> getAllUsers() throws UserDAOException;
}
