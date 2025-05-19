package org.ri.se.vt.v3;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionStatusList extends ArrayList<TransactionStatus> {
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			Logger logger_ = LoggerFactory.getLogger(TransactionStatusList.class);
			logger_.error("Problems when serializing (json) RecordStatusList object !");
			return null;
		}
	}
}
