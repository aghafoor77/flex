package org.ri.se.vt.blockchain;

import java.util.Objects;

import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3JConnector {

	static Logger log = LoggerFactory.getLogger(Web3JConnector.class.getName());

	private Web3j web3 = null;
	private String url = null;

	

	public Web3JConnector(String url) {
		this.url = url;
	}

	public Web3j getWeb3j() throws EtherGenericException {
		
		if (Objects.isNull(url)) {
			log.error("URL is not defined, please call constructor once before geting web3j instance !");
			throw new EtherGenericException(
					"URL is not defined, please call constructor once before geting web3j instance !");
		}

		if (Objects.isNull(web3)) {
			log.info("Creating new Web3 Object !");
			web3 = Web3j.build(new HttpService(url));
		}
		return web3;
	}
}
