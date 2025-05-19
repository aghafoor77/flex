package org.ri.se.verifiablecredentials.entities;

import java.net.URI;

public class VCVerificationMethod {
	private String type;
    private URI id;
    private String publicKeyBase58;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public URI getId() {
		return id;
	}
	public void setId(URI id) {
		this.id = id;
	}
	public String getPublicKeyBase58() {
		return publicKeyBase58;
	}
	public void setPublicKeyBase58(String publicKeyBase58) {
		this.publicKeyBase58 = publicKeyBase58;
	}
    
    
    
}
