package org.ri.se.vt.contract;

import java.util.Objects;

import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.CredentialException;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class ContractDeployer {

	private static Logger logger_ = LoggerFactory.getLogger(ContractDeployer.class);
	private Web3JConnector web3JConnector = null;

	public ContractDeployer(String contractLocatorURL) {
		web3JConnector = new Web3JConnector(contractLocatorURL);
	}

	public String deployTraceabilityContract(String walletDir, String username, String password)
			throws EtherGenericException, CredentialException {

		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);

		try {

			logger_.debug("Deploying traceability credentials !");

			SCTraceability contract = SCTraceability
					.deploy(web3JConnector.getWeb3j(), cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();

			if (Objects.isNull(contract)) {
				throw new EtherGenericException("Problems when deploying smart contract !");
			}

			logger_.debug("Deployed smart contract address is : " + contract.getContractAddress());
			return contract.getContractAddress();

		} catch (Exception e) {

			logger_.error("Error occurred during traceability smart contract deployment !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	public String deployTraceabilityContractV2(String walletDir, String username, String password)
			throws EtherGenericException, CredentialException {

		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);

		try {

			logger_.debug("Deploying traceability V2 credentials !");

			SCTraceabilityV2 contract = SCTraceabilityV2
					.deploy(web3JConnector.getWeb3j(), cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();

			if (Objects.isNull(contract)) {
				throw new EtherGenericException("Problems when deploying smart contract V2 !");
			}

			logger_.debug("Deployed smart contract address is : " + contract.getContractAddress());
			return contract.getContractAddress();

		} catch (Exception e) {

			logger_.error("Error occurred during traceability smart contract deployment !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}
	   
	public String deployTraceabilityContractV3(Credentials cred)
			throws EtherGenericException, CredentialException {

		try {

			logger_.debug("Deploying traceability V3 credentials !");

			SCTraceabilityV3 contract = SCTraceabilityV3
					.deploy(web3JConnector.getWeb3j(), cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();

			if (Objects.isNull(contract)) {
				throw new EtherGenericException("Problems when deploying smart contract V3 !");
			}

			logger_.debug("Deployed smart contract address is : " + contract.getContractAddress());
			return contract.getContractAddress();

		} catch (Exception e) {

			logger_.error("Error occurred during traceability smart contract deployment !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	
}
