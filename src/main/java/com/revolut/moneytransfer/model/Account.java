package com.revolut.moneytransfer.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	private final long accountId;

	private final String userName;

	private final BigDecimal balance;

	private final String currencyCode;

	@JsonCreator
	public Account(@JsonProperty("accountId") final long accountId,
			@JsonProperty("username") final String username,
			@JsonProperty("balance") final BigDecimal balance,
			@JsonProperty("currencyCode") final String currencyCode) {
		this.accountId = accountId;
		this.userName = username;
		this.balance = balance;
		this.currencyCode = currencyCode;
	}

	public long getAccountId() {
		return accountId;
	}

	public String getUserName() {
		return userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Account account = (Account) o;

		if (accountId != account.accountId)
			return false;
		if (!userName.equals(account.userName))
			return false;
		if (!balance.equals(account.balance))
			return false;
		return currencyCode.equals(account.currencyCode);

	}

	@Override
	public int hashCode() {
		int result = (int) (accountId ^ (accountId >>> 32));
		result = 31 * result + userName.hashCode();
		result = 31 * result + balance.hashCode();
		result = 31 * result + currencyCode.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Account{" + "accountId=" + accountId + ", userName='" + userName + '\'' + ", balance=" + balance
				+ ", currencyCode='" + currencyCode + '\'' + '}';
	}
}
