package com.ri.se.dap.exceptions;

public class CredentialException extends Exception {

	public CredentialException(Exception exp) {
		super(exp);
	}
	public CredentialException(String message) {
		super(message);
	}
}