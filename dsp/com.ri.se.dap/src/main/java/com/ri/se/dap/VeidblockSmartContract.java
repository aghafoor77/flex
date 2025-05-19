package com.ri.se.dap;

import org.web3j.crypto.Credentials;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import com.ri.se.dap.exceptions.EtherGenericException;

public class VeidblockSmartContract {

	private Web3JConnector web3JConnector = null;

	public VeidblockSmartContract(String contractLocatorURL) {
		web3JConnector = new Web3JConnector(contractLocatorURL);
	}

	public VeidblockSmartContract() {
	}

	public String deployContract(String walletDir, String username, String password)
			throws EtherGenericException, Exception {

		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);
		Vblock contract;
		try {
			contract = Vblock.deploy(web3JConnector.getWeb3j(), cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT)
					.send();
			return contract.getContractAddress();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public String deployContract(Credentials credentials, Web3JConnector web3JConnector) throws EtherGenericException {
		Vblock contract;
		try {
			contract = Vblock
					.deploy(web3JConnector.getWeb3j(), credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT)
					.send();
			return contract.getContractAddress();
		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public Vblock getContract(String contractAddress, String walletDir, String username, String password,
			Web3JConnector web3JConnector) throws EtherGenericException {
		try {
			Vblock veidblock = Vblock.load(contractAddress, web3JConnector.getWeb3j(),
					new AccountsManager().getCredentials(walletDir, username, password), ManagedTransaction.GAS_PRICE,
					Contract.GAS_LIMIT);
			return veidblock;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EtherGenericException(e);
		}
	}

	public Vblock getContract(String contractAddress, String privateKey, Web3JConnector web3JConnector)
			throws EtherGenericException {
		try {
			// ContractGasProvider contractGasProvider = new DefaultGasProvider();

			Vblock veidblock = Vblock.load(contractAddress, web3JConnector.getWeb3j(),
					new AccountsManager().createCredentilsFromPrivateKey(privateKey), ManagedTransaction.GAS_PRICE,
					Contract.GAS_LIMIT);
			return veidblock;

		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}

	public Vblock getContract(String contractAddress, Credentials credentials, Web3JConnector web3JConnector)
			throws EtherGenericException {
		try {
			// ContractGasProvider contractGasProvider = new DefaultGasProvider();

			Vblock veidblock = Vblock.load(contractAddress, web3JConnector.getWeb3j(), credentials,
					ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			return veidblock;

		} catch (Exception e) {
			throw new EtherGenericException(e);
		}
	}
}
