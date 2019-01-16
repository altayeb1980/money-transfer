package com.revolut.moneytransfer.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.revolut.moneytransfer.model.Account;

public class AccountMapper implements RowMapper<Account>{
	public Account map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Account(rs.getLong("AccountId"),rs.getString("UserName"), rs.getBigDecimal("Balance"), rs.getString("CurrencyCode"));
	}
}
