package com.revolut.moneytransfer.exception;

public class ErrorResponse {

	private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
