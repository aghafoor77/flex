package org.ri.se.ipfsj.lsc;

import java.io.FileNotFoundException;

public class ResourceNotFoundException extends FileNotFoundException {
	
	public ResourceNotFoundException(Exception exp) {
		super();
	}
	public ResourceNotFoundException(String message) {
		super(message);
	}
}