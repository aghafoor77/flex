package org.ri.se.vt.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecordStatus {
	static Logger log = LoggerFactory.getLogger(RecordStatus.class.getName());
	private String id;
	private long no;
	public enum Role {
		OWNER("owner"), PARTICIPATED("participated"), NONE("none");

		String value;

		Role(String _value) {
			value = _value;
		}

		public String value() {
			return value;
		}
	}
	
	private Role role;
	
	public RecordStatus() {
		super();
	}

	public RecordStatus(String id, long no, Role role) {
		super();
		this.id = id;
		this.no = no;
		this.role = role;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			
			log.error("Problems when serializing (json) RecordStatus object !");
			return null;
		}
	}
}
