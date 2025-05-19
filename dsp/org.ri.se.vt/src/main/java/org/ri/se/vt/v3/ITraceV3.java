package org.ri.se.vt.v3;

import java.util.ArrayList;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceabilityVX;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface ITraceV3 {
	
	public TransactionReceipt createBaseTransaction(String _id, String _data, ArrayList<String> _parents, String _other) throws EtherGenericException ;
	public TransactionReceipt appendTransaction(String _id, String _receiver, String _data) throws EtherGenericException;
	public TransactionReceipt appendTransaction(Transaction transaction) throws EtherGenericException;
	public BaseTransaction fetchBaseTransaction(String _id) throws EtherGenericException;
	public Transaction fetchTransaction(String _id, long _no) throws EtherGenericException;
	public String owner(String _id) throws EtherGenericException;
	public TraceabilityList get(String _id) throws EtherGenericException;
	public long size(String _id) throws EtherGenericException;
	public TransactionStatus getTransactionStatus(String _id, long _no) throws EtherGenericException;
	public TransactionStatusList getTransactionStatus(String _id) throws EtherGenericException;
	public long totalIds() throws EtherGenericException;
	
	public String getIdString(long idNo) throws EtherGenericException;
	public TransactionStatusList getTransactionStatusOwnedByMe() throws TraceabilityException, EtherGenericException;
	public TransactionStatusList getTransactionStatusOfAllIds() throws TraceabilityException, EtherGenericException;
	public long geTotalTransLinkById(String _id)
			throws EtherGenericException;
	
	public TransactionReceipt addTransLink(String _id, String recipient, String transHashs)
			throws EtherGenericException;
	
	public String getTransLink(String _id, long tno)	throws EtherGenericException;
	public SCTraceabilityVX getSCTraceabilityVX() throws Exception;
	
	public EthBlockNumber getBlockNumber() throws Exception;
	public void getParentGraph(String id, int i)throws Exception;
	
	public boolean isSmartContractExists();

}