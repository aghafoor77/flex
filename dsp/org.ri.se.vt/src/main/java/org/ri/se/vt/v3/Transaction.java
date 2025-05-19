package org.ri.se.vt.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Transaction {

	private String id;
	private String sender;
	private String receiver;
	private String transactionData;

	public Transaction() {

	}

	public Transaction(String id, String receiver, String transactionData) {
		super();
		this.id = id;
		this.receiver = receiver;
		this.transactionData = transactionData;
	}

	public Transaction(String id, String sender, String receiver, String transactionData) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.transactionData = transactionData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	
	public String getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(String transactionData) {
		this.transactionData = transactionData;
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

}