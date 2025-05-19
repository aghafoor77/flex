package org.ri.se.vt.v3;



import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseTransaction {

	
	private String id;
	private String sender;
	private String data;
	private ArrayList<String> parents;
	private String description;

	public BaseTransaction() {

	}

	public BaseTransaction(String id, String data, ArrayList<String> parents, String description) {
		this.id = id;
		this.data = data;
		this.parents = parents;
		this.description = description;
		sender = null;
	}
	
	public BaseTransaction(String sender, String id, String data, ArrayList<String> parents, String description) {
		this.id = id;
		this.data = data;
		this.parents = parents;
		this.description = description;
		this.sender = sender;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ArrayList<String> getParents() {
		return parents;
	}

	public void setParents(ArrayList<String> parents) {
		this.parents = parents;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			Logger logger_ = LoggerFactory.getLogger(BaseTransaction.class);
			logger_.error("Problems when serializing (json) Record object !");
			return null;
		}
	}
	
}