package org.ri.se.vt.v2;

import java.math.BigInteger;
import java.util.Objects;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.NoRecordFoundException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceabilityV2;
import org.ri.se.vt.v1.Record;
import org.ri.se.vt.v1.RecordList;
import org.ri.se.vt.v1.RecordStatus;
import org.ri.se.vt.v1.RecordStatus.Role;
import org.ri.se.vt.v1.RecordStatusList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class TraceV2 implements ITraceV2 {
	private static final Logger logger_ = LoggerFactory.getLogger(TraceV2.class);
	private SCTraceabilityV2 contract = null;
	private Credentials owner = null;
	private Web3JConnector web3JConnector;

	/**
	 * 
	 * @param web3JConnector
	 * @param contractAddress
	 * @param owner
	 * @throws EtherGenericException
	 */
	public TraceV2(Web3JConnector web3JConnector, String contractAddress, Credentials owner)
			throws EtherGenericException {
		try {
			this.web3JConnector = web3JConnector;
			contract = SCTraceabilityV2.load(contractAddress, web3JConnector.getWeb3j(), owner,
					ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			this.owner = owner;
			logger_.debug("Owner Address : " + owner.getAddress());
			// System.err.println("Owner Address : " + owner.getAddress());
		} catch (Exception e) {

			logger_.error("Problems when loading contract !");
			logger_.error(e.getMessage());

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

			logger_.error("Problems when adding record !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	@Override
	public TransactionReceipt set(Record record) throws EtherGenericException {
		isContractNull();
		try {
			return set(record.getId(), record.getReceiver(), record.getData(), record.getParents(), record.getOther());

		} catch (Exception e) {
			logger_.error("Problems when adding (param) contract !");
			logger_.error(e.getMessage());
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
				logger_.warn("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find record ! {_id:" + _id + ", no:" + _no + "}");
			}

			Record record = new Record(t5.component1(), _id, t5.component2(), t5.component3(), t5.component4(),
					t5.component5());
			// System.out.println(record.toString());
			return record;
		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No record found ! {_id:" + _id + ", no:" + _no + "}");
		} catch (Exception e) {
			logger_.error("Problems when fetching record ! {_id:" + _id + ", no:" + _no + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching record ! {_id:" + _id + ", no:" + _no + "}");
		}
	}

	@Override
	public RecordList get(String _id) throws EtherGenericException {
		logger_.debug("In case of problem, please debug 'get with id' function !");
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
			logger_.error("Problems when fetching size of the stack against ! {_id:" + _id + "}");
			logger_.error(e.getMessage());
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
			logger_.error("Problems when fetching size of the stack against !");
			logger_.error(e.getMessage());
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
			logger_.error("Problems when fetching size of the stack against !");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against !");
		}
	}

	@Override
	public RecordStatus getRescordStatus(String _id, long _no) throws EtherGenericException {
		logger_.debug("In case of problem, please debug 'getRescordStatus with id and no' function !");
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
		logger_.debug("In case of problem, please debug 'getRescordStatus with id' function !");
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
			logger_.error("Smart contract address is null !");
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
		RecordStatus recordStatus = getRescordStatus(_id, lastIdNo - 1);
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
			if (!Objects.isNull(recordStatus))
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
				RecordStatus recordStatus = getRescordStatus(id, idSize - 1);
				recordStatusList.add(recordStatus);
			}
		}
		return recordStatusList;
	}

	@Override
	public long countTransaction(Web3JConnector web3JConnector, String contractAddress, String _id)
			throws EtherGenericException {

		try {
			BigInteger count = contract.geTotalTransactionById(_id).send();
			return count.longValueExact();
		} catch (Exception e) {
			logger_.error("Problems when fetching size of the stack against ! {_id:" + _id + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against ! {_id:" + _id + "}");
		}

	}

	@Override
	public TransactionReceipt addTransaction(Web3JConnector web3jConnector, String contractAddress, String _id,
			String _receiver, String transHashs) throws EtherGenericException {
		isContractNull();

		try {

			return contract.setTransaction(_id, _receiver, transHashs).send();

		} catch (Exception e) {

			logger_.error("Problems when adding transaction !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}

	}

	@Override
	public String getTransaction(Web3JConnector web3jConnector, String contractAddress, String _id, long tno)
			throws EtherGenericException {
		isContractNull();
		try {
			Tuple2<String, String> t2 = contract.getTransation(_id, new BigInteger(Long.toString(tno))).send();
			if (Objects.isNull(t2)) {
				logger_.warn("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find record ! {_id:" + _id + ", no:" + tno + "}");
			}

			return t2.component2();

		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find record ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No record found ! {_id:" + _id + ", no:" + tno + "}");
		} catch (Exception e) {
			logger_.error("Problems when fetching record ! {_id:" + _id + ", no:" + tno + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching record ! {_id:" + _id + ", no:" + tno + "}");
		}
	}

	@Override
	public SCTraceabilityV2 getSCTraceabilityV2() throws Exception {
		// TODO Auto-generated method stub
		return contract;
	}

	public EthBlockNumber getBlockNumber() throws Exception {
		EthBlockNumber result = new EthBlockNumber();
		result = web3JConnector.getWeb3j().ethBlockNumber().sendAsync().get();
		return result;
	}
}
