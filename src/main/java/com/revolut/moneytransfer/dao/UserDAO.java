package com.revolut.moneytransfer.dao;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import com.revolut.moneytransfer.mapper.UserMapper;
import com.revolut.moneytransfer.model.User;

@RegisterRowMapper(UserMapper.class)
public interface UserDAO {
	String TABLE_NAME = "User";
	String SQL_GET_ALL_USER = "SELECT * FROM " + TABLE_NAME;
	
	String SQL_GET_USER_BY_EMAIL = "SELECT * FROM " + TABLE_NAME + " WHERE emailAddress = :emailAddress";
	
	@SqlQuery(SQL_GET_ALL_USER)
	List<User> findAll();
	
	
	@SqlQuery(SQL_GET_USER_BY_EMAIL)
	Optional<User> findByEmailAddress(@Bind("emailAddress") String emailAddress);
}
