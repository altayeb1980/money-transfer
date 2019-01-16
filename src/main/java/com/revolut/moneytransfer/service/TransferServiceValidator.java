package com.revolut.moneytransfer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import com.revolut.moneytransfer.exception.InsufficientFundsException;
import com.revolut.moneytransfer.exception.InvalidCurrencyException;
import com.revolut.moneytransfer.exception.TransferFailureException;

public class TransferServiceValidator {

	private static final BigDecimal ZERO_AMOUNT = new BigDecimal(0).setScale(4, RoundingMode.HALF_EVEN);

	public void validateCcyCode(String inputCcyCode) {
		try {
			Currency instance = Currency.getInstance(inputCcyCode);
			if (!instance.getCurrencyCode().equals(inputCcyCode)) {
				throw new TransferFailureException("Cannot parse the input Currency Code, Validation Failed");
			}
		} catch (Exception e) {
			throw new TransferFailureException("Cannot parse the input Currency Code, Validation Failed:", e);
		}
	}

	public void validateTransactionCurrency(String username, String currencyCode, String transactionCurrency) {
		if (!currencyCode.equals(transactionCurrency)) {
			throw new InvalidCurrencyException(username);
		}
	}

	public void validateInsufficientFunds(BigDecimal accountLeftOverAmount, String accountId) {
		if (accountLeftOverAmount.compareTo(ZERO_AMOUNT) < 0) {
			throw new InsufficientFundsException(accountId);
		}
	}
}
