package org.ri.se.vt.v1;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Record {

	
	private String id;
	private String sender;
	private String receiver;
	private String data;
	private String parents;
	private String other;

	public Record() {

	}

	public Record(String id, String receiver, String data, String parents, String other) {
		this.id = id;
		this.receiver = receiver;
		this.data = data;
		this.parents = parents;
		this.other = other;
		sender = null;
	}
	
	public Record(String sender, String id, String receiver, String data, String parents, String other) {
		this.id = id;
		this.receiver = receiver;
		this.data = data;
		this.parents = parents;
		this.other = other;
		this.sender = sender;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
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
			
			System.err.println("Problems when serializing (json) Record object !");
			return null;
		}
	}
	
}