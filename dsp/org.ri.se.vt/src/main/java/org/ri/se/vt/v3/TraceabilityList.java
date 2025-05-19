package org.ri.se.vt.v3;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TraceabilityList {
	
	private String id;
	private String initiator;
	private String data;
	private ArrayList<String> parents;
	private String description;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getInitiator() {
		return initiator;
	}


	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ArrayList<String> getParents() {
		return parents;
	}


	public void setParents(ArrayList<String> parents) {
		this.parents = parents;
	}


	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}


	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			Logger logger_ = LoggerFactory.getLogger(Transaction.class);
			logger_.error("Problems when serializing (json) Transaction object !");
			return null;
		}
	}
	
	public String serialize() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}


}
