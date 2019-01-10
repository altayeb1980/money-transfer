package com.revolut.moneytransfer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.revolut.moneytransfer.dao.AccountDAO;
import com.revolut.moneytransfer.dao.h2.H2DAOFactory;
import com.revolut.moneytransfer.exception.AccountDAOException;
import com.revolut.moneytransfer.model.Account;

public class AccountDAOImpl implements AccountDAO {
	private final static String SQL_GET_ALL_ACC = "SELECT * FROM Account";
	
	private static Logger log = Logger.getLogger(AccountDAOImpl.class);
	
	@Override
	public List<Account> getAllAccounts() throws AccountDAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Account> allAccounts = new ArrayList<Account>();
		try {
			conn = H2DAOFactory.getConnection();
			stmt = conn.prepareStatement(SQL_GET_ALL_ACC);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Account acc = new Account(rs.getLong("AccountId"), rs.getString("UserName"),
						rs.getBigDecimal("Balance"), rs.getString("CurrencyCode"));
				if (log.isDebugEnabled())
					log.debug("getAllAccounts(): Get  Account " + acc);
				allAccounts.add(acc);
			}
			return allAccounts;
		} catch (SQLException e) {
			throw new AccountDAOException("getAccountById(): Error reading account data", e);
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}
	}

}
