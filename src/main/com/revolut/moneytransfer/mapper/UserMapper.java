package com.revolut.moneytransfer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.revolut.moneytransfer.model.User;

public class UserMapper implements RowMapper<User>{
	public User map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new User(rs.getLong("UserId"),rs.getString("UserName"), rs.getString("EmailAddress"));
	}
}
