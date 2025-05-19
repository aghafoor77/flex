package org.ri.se.vt.blockchain.exceptions;

public class EtherGenericException extends Exception {

	public EtherGenericException(Exception exp) {
		super(exp);
	}
	public EtherGenericException(String message) {
		super(message);
	}
}