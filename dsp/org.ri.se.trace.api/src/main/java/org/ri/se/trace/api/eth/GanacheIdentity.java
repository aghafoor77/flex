package org.ri.se.trace.api.eth;

public class GanacheIdentity {
	// Manually copied private key from ganache (account 0)
	public String privateKeyToBorrowMoney;

	// Manually copied public key (Account 1) from ganache
	public String admin;

	// Manually copied private key (Account 1) from ganache
	public String privateKyeOfAdmin;

	// Manually copied public key (Account 3) from ganache
	public String userAddress;

	// Manually copied private key (Last Account) from ganache
	public String otherPrivateKey;

	public String token;
	
	public String[] otherPublicAddresses; 
	
	public String getPrivateKeyToBorrowMoney() {
		return privateKeyToBorrowMoney;
	}

	public void setPrivateKeyToBorrowMoney(String privateKeyToBorrowMoney) {
		this.privateKeyToBorrowMoney = privateKeyToBorrowMoney;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getPrivateKyeOfAdmin() {
		return privateKyeOfAdmin;
	}

	public void setPrivateKyeOfAdmin(String privateKyeOfAdmin) {
		this.privateKyeOfAdmin = privateKyeOfAdmin;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getOtherPrivateKey() {
		return otherPrivateKey;
	}

	public void setOtherPrivateKey(String otherPrivateKey) {
		this.otherPrivateKey = otherPrivateKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String[] getOtherPublicAddresses() {
		return otherPublicAddresses;
	}

	public void setOtherPublicAddresses(String[] otherPublicAddresses) {
		this.otherPublicAddresses = otherPublicAddresses;
	}
	
	
	
}
