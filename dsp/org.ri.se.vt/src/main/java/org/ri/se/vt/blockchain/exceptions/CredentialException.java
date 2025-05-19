package org.ri.se.vt.blockchain.exceptions;

public class CredentialException extends Exception {

	public CredentialException(Exception exp) {
		super(exp);
	}
	public CredentialException(String message) {
		super(message);
	}
}