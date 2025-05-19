package org.ri.se.vt.v1;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RecordList extends ArrayList<Record> {
	static Logger log = LoggerFactory.getLogger(RecordList.class.getName());

	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			
			log.error("Problems when serializing (json) RecordList object !");
			return null;
		}
	}
}
