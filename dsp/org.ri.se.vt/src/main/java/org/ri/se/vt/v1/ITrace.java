package org.ri.se.vt.v1;

import java.util.List;

import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceability;
import org.ri.se.vt.contract.SCTraceabilityV2;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface ITrace {
	
	public TransactionReceipt set(String _id, String _receiver, String _data, String _parents, String _other) throws EtherGenericException;
	public TransactionReceipt set(Record record) throws EtherGenericException;
	public Record get(String _id, long _no) throws EtherGenericException;
	public String owner(String _id) throws EtherGenericException;
	public RecordList get(String _id) throws EtherGenericException;
	public long size(String _id) throws EtherGenericException;
	//public RecordStatusList getRescordStatus() throws TraceabilityException, EtherGenericException;
	public RecordStatus getRescordStatus(String _id, long _no) throws EtherGenericException;
	public RecordStatusList getRescordStatus(String _id) throws EtherGenericException;
	public long totalIds() throws EtherGenericException;
	
	public String getIdString(long idNo) throws EtherGenericException;
	public List<String> getIds() throws TraceabilityException, EtherGenericException;
	public RecordStatusList getRescordStatusOwnedByMe() throws TraceabilityException, EtherGenericException;
	public RecordStatusList getRescordStatusOfAllIds() throws TraceabilityException, EtherGenericException;
	public boolean isSmartContractExists();
	
	public SCTraceability getSCTraceability() throws Exception;

}