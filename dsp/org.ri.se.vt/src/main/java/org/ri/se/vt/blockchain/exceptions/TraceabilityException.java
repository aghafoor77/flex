package org.ri.se.vt.blockchain.exceptions;

public class TraceabilityException extends Exception {

	public TraceabilityException(Exception exp) {
		super(exp);
	}
	public TraceabilityException(String message) {
		super(message);
	}
}