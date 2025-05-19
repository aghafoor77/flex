package org.ri.se.trace.api.eth;

public class EtherAccountData {

	private String username;
	private String address;
	private double ether;
	private String rsaPublicKeyPem;
	private String rsaPublicKeyHex;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getEther() {
		return ether;
	}
	public void setEther(double ether) {
		this.ether = ether;
	}
	public String getRsaPublicKeyPem() {
		return rsaPublicKeyPem;
	}
	public void setRsaPublicKeyPem(String rsaPublicKeyPem) {
		this.rsaPublicKeyPem = rsaPublicKeyPem;
	}
	public String getRsaPublicKeyHex() {
		return rsaPublicKeyHex;
	}
	public void setRsaPublicKeyHex(String rsaPublicKeyHex) {
		this.rsaPublicKeyHex = rsaPublicKeyHex;
	}
}
