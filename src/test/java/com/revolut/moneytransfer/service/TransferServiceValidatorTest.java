package com.revolut.moneytransfer.service;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.revolut.moneytransfer.exception.InsufficientFundsException;
import com.revolut.moneytransfer.exception.InvalidCurrencyException;
import com.revolut.moneytransfer.exception.TransferFailureException;

public class TransferServiceValidatorTest {

	private TransferServiceValidator transferServiceValidator;
	
	@Before
	public void setup() {
		transferServiceValidator = new TransferServiceValidator();
	}
	
	
	@Test(expected=TransferFailureException.class)
	public void testWhenCurrencyInvalidValidateCcyCode(){
		transferServiceValidator.validateCcyCode("USA");
	}
	
	@Test(expected=InvalidCurrencyException.class)
	public void testValidateTransactionCurrency() {
		transferServiceValidator.validateTransactionCurrency("altayeb", "USD", "EUR");
	}

	
	@Test(expected=InsufficientFundsException.class)
	public void validateInsufficientFunds() {
		transferServiceValidator.validateInsufficientFunds(new BigDecimal(-2), "100");
	}
	
	
}
