package com.ri.se.dap;

import java.math.BigInteger;
import java.util.Objects;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.ri.se.dap.exceptions.EtherGenericException;

public class VeidblockManager {

	private Web3JConnector web3JConnector = null;

	public VeidblockManager(String url) {
		web3JConnector = new Web3JConnector(url);
	}

	public String owner(String contractAddress, String privateKey) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.owner().send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String owner(String contractAddress, Credentials credentials) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, credentials, web3JConnector);
			if (Objects.isNull(supper.getContractAddress())) {
				throw new EtherGenericException("Smart contract not found !");
			}
			return supper.owner().send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String giveUserRights(String contractAddress, String privateKey, String userAddress, String token)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			TransactionReceipt bolAdmin = supper.giveUserRights(userAddress, token).send();
			return bolAdmin.getStatus();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String giveUserRights(String contractAddress, String walletDir, String username, String password,
			String userAddress, String token) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, walletDir, username, password,
					web3JConnector);
			TransactionReceipt bolAdmin = supper.giveUserRights(userAddress, token).send();
			return bolAdmin.getStatus();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public boolean verify(String contractAddress, String privateKey, String token) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.verify(token).send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String publickey(String contractAddress, String privateKey, String token) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.publickey(token).send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String findAdminOfUser(String contractAddress, String privateKey, String token)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.findAdminOfUser(token).send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public BigInteger totalUsers(String contractAddress, String privateKey) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.totalUsers().send();

		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public boolean isAdmin(String contractAddress, String privateKey, String address) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.isAdmin(address).send();

		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String giveAdminRights(String contractAddress, String privateKey, String adminAddress)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			TransactionReceipt bolAdmin = supper.giveAdminRights(adminAddress).send();
			return bolAdmin.getStatus();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String giveAdminRights(String contractAddress, String walletDir, String username, String password,
			String adminAddress) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, walletDir, username, password,
					web3JConnector);
			TransactionReceipt bolAdmin = supper.giveAdminRights(adminAddress).send();
			return bolAdmin.getStatus();
		} catch (Exception e) {
			e.printStackTrace();
			throw new EtherGenericException(e);
		}
	}

	public BigInteger totalAdmins(String contractAddress, String privateKey) throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.totalAdmins().send();

		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	// ----------------Manage RSA key of user ---------------------------
	public String addRSAPublicKey(String contractAddress, String privateKey, String useraddress)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			TransactionReceipt rsaPubkey = supper.addRSAKey(useraddress).send();
			return rsaPubkey.getStatus();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String getRSAPublicKey(String contractAddress, String privateKey, String address)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, privateKey, web3JConnector);
			return supper.getRSAKey(address).send();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String getRSAPublicKey(String contractAddress, Credentials credentials, String address)
			throws EtherGenericException {
		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, credentials, web3JConnector);
			String key = supper.getRSAKey(address).send();
			if(Objects.isNull(key) || key.length() == 0) {
				throw new EtherGenericException("RSA public key of user '"+address+"' not found !");
			}
			return key;
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}
	
	public boolean isSmartContractExists(String contractAddress, Credentials credentials) {

		try {
			Vblock supper = new VeidblockSmartContract().getContract(contractAddress, credentials, web3JConnector);
			if (Objects.isNull(supper.getContractAddress())) {
				return false;
			}
			String owner = supper.owner().send();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
