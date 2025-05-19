package org.ri.se.trace.api.eth;

public class EtherProps {

	private String credentialsPath = "/home/blockchain/Desktop/tvsp/traceability/org.ri.se.trace/src/main/resources";
	private String password = "1122334455";
	private String username = "abdul";
	private String etherURL = "http://localhost:8545";
	private String storePath = "/home/blockchain/eth/store.txt";
	public String getCredentialsPath() {
		return credentialsPath;
	}
	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEtherURL() {
		return etherURL;
	}
	public void setEtherURL(String etherURL) {
		this.etherURL = etherURL;
	}
	public String getStorePath() {
		return this.storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	
	
	
}
