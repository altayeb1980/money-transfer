package com.revolut.moneytransfer.dao;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.revolut.moneytransfer.mapper.AccountMapper;
import com.revolut.moneytransfer.model.Account;


@RegisterRowMapper(AccountMapper.class)
public interface AccountDAO {
	String TABLE_NAME = "Account";
	String SQL_GET_ALL_ACC = "SELECT * FROM " + TABLE_NAME;
	String SQL_GET_ACC_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE AccountId = :accountId";
	String SQL_UPDATE_ACC_BALANCE = "UPDATE " + TABLE_NAME
			+ " SET Balance = :balance WHERE AccountId = :accountId ";
	
	@SqlQuery(SQL_GET_ALL_ACC)
	List<Account> getAll();
	@SqlQuery(SQL_GET_ACC_BY_ID)
	Optional<Account> findById(@Bind("accountId") long accountId);
	@SqlUpdate(SQL_UPDATE_ACC_BALANCE)
	int updateAccountBalance(@BindBean Account account);
}
