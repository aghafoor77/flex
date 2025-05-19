package org.ri.se.ipfsj.lsc;

public class IPFSConnectionException extends Exception{
	
	public IPFSConnectionException (Exception exp) {
		super();
	}
	public IPFSConnectionException (String message) {
		super(message);
	}
}