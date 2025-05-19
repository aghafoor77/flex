package org.ri.se.trace.api.register;

import java.io.IOException;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ri.se.trace.api.main.TAPIConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterSmartContract {
	
	public void registerSmartContractAddress(TAPIConfig tapiConfig, String smartContractName, String smartContractAddress) throws Exception {
		String url = tapiConfig.getUrlCentraliRegistration() + "/application/v1/centralhostregistration";
		ConfigUnit configUnit = new ConfigUnit();
		configUnit .setUrl(url);
		configUnit.setKey(smartContractName); 
		configUnit.setRequired("smartcontract");
		configUnit.setValue(smartContractAddress);
		configUnit.setType("smartcontract");
		registerHost(configUnit);
	}

	public void registerHost(ConfigUnit configUnit) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Registering smart contract with centrali registry !");
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configUnit));
		submit(configUnit);
	}

	public boolean submit(ConfigUnit configUnit) {

		if (Objects.isNull(configUnit)) {
			System.err.println("Input object (ConfigUnit) is null !");
			return false;
		}
		if (Objects.isNull(configUnit.getRequired())) {
			System.err.println("Invalid configuration for this utility. Required field of configuratin is missing !");
			return false;
		}

		String serialized = toJSON(configUnit);
		if (Objects.isNull(serialized)) {
			System.err.println("Problems when serializing ConfigUnit object !");
			return false;
		}
		try {
			System.out.println("Sending smart contract data ... ");
			sendPost(configUnit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(serialized);
		return true;
	}

	public String toJSON(ConfigUnit cu) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(cu);
		} catch (JsonProcessingException e) {

			return null;
		}
	}

	public boolean sendPost(ConfigUnit configUnit) {
		System.out.println("Sending post data to " + configUnit.getUrl());

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(configUnit.getUrl());
			post.setHeader("Content-type", "application/json");
			StringEntity input = new StringEntity(new ObjectMapper().writeValueAsString(configUnit));
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.err.println("Problems when send post request to '" + configUnit.getUrl() + "' for "
						+ configUnit.getRequired() + " key " + configUnit.getKey() + " !");
				return false;
			}
			System.out.println("Successfully posted following data !");
			System.out.println(new ObjectMapper().writeValueAsString(configUnit));
			System.out.println("====================================================================!\n\n");
		} catch (Exception exp) {
			System.err.println("Problems when creating connection to " + configUnit.getUrl() + " !");
			System.err.println("Exception message : " + exp.getMessage());
			exp.printStackTrace();
		}
		return true;
	}
}
