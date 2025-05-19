package org.ri.se.vt.v2;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceabilityV2;
import org.ri.se.vt.v1.Record;
import org.ri.se.vt.v1.RecordList;
import org.ri.se.vt.v1.RecordStatus;
import org.ri.se.vt.v1.RecordStatusList;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface ITraceV2 {
	
	public TransactionReceipt set(String _id, String _receiver, String _data, String _parents, String _other) throws EtherGenericException;
	public TransactionReceipt set(Record record) throws EtherGenericException;
	public Record get(String _id, long _no) throws EtherGenericException;
	public String owner(String _id) throws EtherGenericException;
	public RecordList get(String _id) throws EtherGenericException;
	public long size(String _id) throws EtherGenericException;
	public RecordStatus getRescordStatus(String _id, long _no) throws EtherGenericException;
	public RecordStatusList getRescordStatus(String _id) throws EtherGenericException;
	public long totalIds() throws EtherGenericException;
	
	public String getIdString(long idNo) throws EtherGenericException;
	public RecordStatusList getRescordStatusOwnedByMe() throws TraceabilityException, EtherGenericException;
	public RecordStatusList getRescordStatusOfAllIds() throws TraceabilityException, EtherGenericException;
	public long countTransaction(Web3JConnector web3jConnector, String contractAddress, String _id)
			throws EtherGenericException;
	
	public TransactionReceipt addTransaction(Web3JConnector web3jConnector, String contractAddress, String _id, String recipient, String transHashs)
			throws EtherGenericException;
	
	public String getTransaction(Web3JConnector web3jConnector, String contractAddress, String _id, long tno)	throws EtherGenericException;
	public SCTraceabilityV2 getSCTraceabilityV2() throws Exception;
	
	public EthBlockNumber getBlockNumber() throws Exception;

}