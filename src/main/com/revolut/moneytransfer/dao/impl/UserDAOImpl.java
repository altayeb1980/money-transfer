package com.revolut.moneytransfer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.revolut.moneytransfer.dao.UserDAO;
import com.revolut.moneytransfer.dao.h2.H2DAOFactory;
import com.revolut.moneytransfer.exception.UserDAOException;
import com.revolut.moneytransfer.model.User;

public class UserDAOImpl implements UserDAO {
	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	private final static String SQL_GET_ALL_USERS = "SELECT * FROM User";

	@Override
	public List<User> getAllUsers() throws UserDAOException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		try {
			conn = H2DAOFactory.getConnection();
			stmt = conn.prepareStatement(SQL_GET_ALL_USERS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				User u = new User(rs.getLong("UserId"), rs.getString("UserName"), rs.getString("EmailAddress"));
				users.add(u);
				if (log.isDebugEnabled())
					log.debug("getAllUsers() Retrieve User: " + u);
			}
			return users;
		} catch (SQLException e) {
			throw new UserDAOException("Error reading user data", e);
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}

	}

}
