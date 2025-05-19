package org.ri.se.trace.api.utils;

import java.util.List;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.v1.ITrace;
import org.ri.se.vt.v1.Record;
import org.ri.se.vt.v1.RecordList;
import org.ri.se.vt.v1.RecordStatus;
import org.ri.se.vt.v1.RecordStatusList;
import org.ri.se.vt.v1.Trace;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class ResourceDelegator {

	private ITrace trace = null;
	public ResourceDelegator(String contract, Credentials credential,String url) throws EtherGenericException {
		Web3JConnector web3JConnector = new Web3JConnector(url);
		trace = new Trace(web3JConnector, contract, credential);
		
	}
	
	public TransactionReceipt set(Record record) throws EtherGenericException {
		return trace.set(record); 	
	}
	public Record get(String _id, long _no) throws EtherGenericException {
		return trace.get(_id, _no); 	
	}
	public RecordList get(String _id) throws EtherGenericException {
		return trace.get(_id);
	}
	public long size(String _id) throws EtherGenericException{
		return trace.size(_id);
	}

	public RecordStatus getRescordStatus(String _id, long _no) throws EtherGenericException{
		return trace.getRescordStatus(_id, _no);
	}
	public RecordStatusList getRescordStatus(String _id) throws EtherGenericException{
		return trace.getRescordStatus(_id);
	}
	public long totalIds() throws EtherGenericException{
		return trace.totalIds();
	}
	
	public String owner(String _id) throws EtherGenericException {
		return trace.owner(_id);
	}
	
	//-----------------------------------------------
	public String getIdString(long idNo) throws EtherGenericException{
		return trace.getIdString(idNo);
	}
	public List<String> getIds() throws TraceabilityException, EtherGenericException{
		return trace.getIds();
	}
	public RecordStatusList getRescordStatusOwnedByMe() throws TraceabilityException, EtherGenericException{
		return trace.getRescordStatusOwnedByMe();
	}
	public RecordStatusList getRescordStatusOfAllIds() throws TraceabilityException, EtherGenericException{
		return trace.getRescordStatusOfAllIds();
	}
	
	

}
