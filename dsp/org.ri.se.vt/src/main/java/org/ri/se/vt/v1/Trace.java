package org.ri.se.vt.v1;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.NoRecordFoundException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceability;
import org.ri.se.vt.v1.RecordStatus.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class Trace implements ITrace {
	
	static Logger log = LoggerFactory.getLogger(Trace.class.getName());
	private SCTraceability contract = null;
	private Credentials owner = null;

	/**
	 * 
	 * @param web3JConnector
	 * @param contractAddress
	 * @param owner
	 * @throws EtherGenericException
	 */
	public Trace(Web3JConnector web3JConnector, String contractAddress, Credentials owner)
			throws EtherGenericException {
		try {

			contract = SCTraceability.load(contractAddress, web3JConnector.getWeb3j(), owner,
					ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			this.owner = owner;
			log.info("Owner Address : " + owner.getAddress());
			//log.error("Owner Address : " + owner.getAddress());
		} catch (Exception e) {

			log.error("Problems when loading contract !");
			log.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	/**
	 * 
	 */
	@Override
	public TransactionReceipt set(String _id, String _receiver, String _data, String _parents, String _other)
			throws EtherGenericException {

		isContractNull();

		try {

			return contract.setRecord(_id, _receiver, _data, _parents, _other).send();

		} catch (Exception e) {

			log.error("Problems when adding record !");
			log.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	@Override
	public TransactionReceipt set(Record record) throws EtherGenericException {
		isContractNull();
		try {
			return set(record.getId(), record.getReceiver(), record.getData(), record.getParents(), record.getOther());

		} catch (Exception e) {
			log.error("Problems when adding (param) contract !");
			log.error(e.getMessage());
			throw new EtherGenericException(e);
		}
	}

	@Override
	public Record get(String _id, long _no) throws EtherGenericException {
		isContractNull();
		try {
			Tuple5<String, String, String, String, String> t5 = contract
					.getRecord(_id, new BigInteger(Long.toString(_no))).send();
			if (Objects.isNull(t5)) {
				log.error("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find record ! {_id:" + _id + ", no:" + _no + "}");
			}
			
			Record record = new Record(t5.component1(), _id, t5.component2(), t5.component3(), t5.component4(),
					t5.component5());
			//System.out.println(record.toString());
			return record;
		} catch (IndexOutOfBoundsException aiob) {
			log.error("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			log.error(aiob.getMessage());
			throw new NoRecordFoundException("No record found ! {_id:" + _id + ", no:" + _no + "}");
		} catch (Exception e) {
			log.error("Problems when fetching record ! {_id:" + _id + ", no:" + _no + "}");
			log.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching record ! {_id:" + _id + ", no:" + _no + "}");
		}
	}

	@Override
	public RecordList get(String _id) throws EtherGenericException {
		log.info("In case of problem, please debug 'get with id' function !");
		isContractNull();
		RecordList recordList = new RecordList();
		long size = size(_id);
		for (long i = 0; i < size; i++) {
			recordList.add(get(_id, i));
		}
		return recordList;
	}

	@Override
	public long size(String _id) throws EtherGenericException {
		isContractNull();
		try {
			BigInteger count = contract.getRecordCountsById(_id).send();
			return count.longValueExact();
		} catch (Exception e) {
			log.error("Problems when fetching size of the stack against ! {_id:" + _id + "}");
			log.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against ! {_id:" + _id + "}");
		}
	}

	@Override
	public long totalIds() throws EtherGenericException {
		isContractNull();
		try {
			BigInteger count = contract.counts().send();
			return count.longValueExact();
		} catch (Exception e) {
			log.error("Problems when fetching size of the stack against !");
			log.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against !");
		}
	}

	@Override
	public String getIdString(long idNo) throws EtherGenericException {
		isContractNull();
		try {
			String rfc = contract.getIdString(new BigInteger("" + idNo)).send();
			return rfc;
		} catch (Exception e) {
			log.error("Problems when fetching size of the stack against !");
			log.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against !");
		}
	}

	@Override
	public List<String> getIds() throws TraceabilityException, EtherGenericException {
		try {
			List<String> list = contract.getIdArray().send();
			return list;
		} catch (Exception e) {
			throw new TraceabilityException(e);
		}
	}

	@Override
	public RecordStatus getRescordStatus(String _id, long _no) throws EtherGenericException {
		log.info("In case of problem, please debug 'getRescordStatus with id and no' function !");
		isContractNull();
		Record record = get(_id, _no);
		RecordStatus rescordStatus = new RecordStatus();
		rescordStatus.setId(_id);
		rescordStatus.setNo(_no);
		if (record.getReceiver().equals(owner.getAddress())) {
			rescordStatus.setRole(Role.OWNER);
		} else if (record.getSender().equals(owner.getAddress())) {
			rescordStatus.setRole(Role.PARTICIPATED);
		} else {
			rescordStatus.setRole(Role.NONE);
		}
		return rescordStatus;
	}

	@Override
	public RecordStatusList getRescordStatus(String _id) throws EtherGenericException {
		log.info("In case of problem, please debug 'getRescordStatus with id' function !");
		isContractNull();
		RecordStatusList recordStatusList = new RecordStatusList();
		long size = size(_id);
		for (long i = 0; i < size; i++) {
			recordStatusList.add(getRescordStatus(_id, i));
		}
		return recordStatusList;
	}

	private boolean isContractNull() throws NullPointerException {
		if (Objects.isNull(contract)) {
			log.error("Smart contract address is null !");
			throw new NullPointerException("Smart contract address is null !");
		}
		return true;
	}

	@Override
	public String owner(String _id) throws EtherGenericException {
		long lastIdNo = size(_id);
		if (lastIdNo == 0) { 
			throw new EtherGenericException("Nor record found exception !");
				
		}
		RecordStatus recordStatus = getRescordStatus(_id, lastIdNo-1);
		if (recordStatus.getRole() == Role.OWNER) {
			return this.owner.getAddress();
		} else {
			return null;
		}
	}

	public RecordStatus ownedRecordStatus(String _id) throws EtherGenericException {
		long lastIdNo = size(_id);
		if (lastIdNo != 0) {
			RecordStatus recordStatus = getRescordStatus(_id, lastIdNo - 1);
			if (recordStatus.getRole() == Role.OWNER) {
				return recordStatus;
			} else {
				return null;
			}
		} else {
			throw new EtherGenericException("Nor record found exception !");
		}
	}

	@Override
	public RecordStatusList getRescordStatusOwnedByMe() throws TraceabilityException, EtherGenericException {
		long l = totalIds();
		RecordStatusList recordStatusList = new RecordStatusList();
		for (long b = 0; b < l; b++) {
			String id = getIdString(b);
			RecordStatus recordStatus = ownedRecordStatus(id);
			if(!Objects.isNull(recordStatus))
				recordStatusList.add(recordStatus);
		}
		return recordStatusList;
	}

	@Override
	public RecordStatusList getRescordStatusOfAllIds() throws TraceabilityException, EtherGenericException {

		long l = totalIds();
		RecordStatusList recordStatusList = new RecordStatusList();
		for (long b = 0; b < l; b++) {
			String id = getIdString(b);
			long idSize = size(id);
			if (idSize != 0) {
				RecordStatus recordStatus = getRescordStatus(id, idSize-1);
				recordStatusList.add(recordStatus);
			}
		}
		return recordStatusList;
	}
	
	public boolean isSmartContractExists() {
		try {
			totalIds();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public SCTraceability getSCTraceability() throws Exception {
		// TODO Auto-generated method stub
		return contract;
	}
}
