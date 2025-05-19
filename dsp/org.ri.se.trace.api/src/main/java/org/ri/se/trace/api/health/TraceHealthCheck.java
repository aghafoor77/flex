
package org.ri.se.trace.api.health;

import java.io.IOException;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import com.codahale.metrics.health.HealthCheck;

public class TraceHealthCheck extends HealthCheck {
	public static Logger logger_ = Logger.getLogger(TraceHealthCheck.class);

	private final Web3JConnector web3JConnector;
	private String urlIPFS;

	public TraceHealthCheck(Web3JConnector web3JConnector, String urlIPFS) {
		super();
		this.web3JConnector = web3JConnector;
		this.urlIPFS = urlIPFS;
	}

	@Override
	protected Result check() throws Exception {
		/*
		 * try { IPFS ipfs = new IPFS(urlIPFS); } catch(Exception exp) {
		 * logger_.error("IPFS is not running so not a healthy application !");
		 * logger_.error(exp); return Result.unhealthy("IPFS is not running !"); }
		 */
		try {
			
			BigInteger blockNo = new BigInteger("0");
			EthBlock result = this.web3JConnector.getWeb3j()
					.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNo), true).send();
			if (result.hasError()) {
				logger_.error("Problems in Ethereum execution !");
				return Result.unhealthy("Ethereum is not running or problems in its communication !");
			}
			return Result.healthy("Ethereum  node is running !");
		} catch (IOException | EtherGenericException e) {
			logger_.error("Ethereum is not running  so not a healthy application !");
			logger_.error(e.getMessage());
			return Result.unhealthy("Ethereum is not running !");
		}
	}
}