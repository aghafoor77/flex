package com.ri.se.dap;

import java.util.Objects;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.ri.se.dap.exceptions.EtherGenericException;

public class Web3JConnector {
	
	private Web3j web3 = null;
	private String url = null;
	public Web3JConnector(String url) {
		this.url = url;
	}
	
	public Web3j getWeb3j() throws EtherGenericException {
		if(Objects.isNull(url)) {
			throw new EtherGenericException("URL is not defined, please call constructor once before geting web3j instance !");
		}
		
		if(Objects.isNull(web3)) {
			web3 = Web3j.build(new HttpService(url));
		} 
		if(Objects.isNull(web3)) {
			throw new EtherGenericException("Problems when creting web3 object for connection !");
		} 
		return web3;
	}
}
