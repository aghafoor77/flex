/**
* <h1>TeaceVX</h1>
* The TeaceVX program implements ITrace interface which 
* provides various features required for traceability purposes.
* 
*
* @author  Abdul Ghafoor Abbasi, abdul.ghafoor@ri.se
* @version 3.0
* @since   2022-05-16
*  
*/

package org.ri.se.vt.v3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

import org.ri.se.vt.blockchain.Web3JConnector;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.NoRecordFoundException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.contract.SCTraceabilityVX;
import org.ri.se.vt.v3.TransactionStatus.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class TraceV3 implements ITraceV3 {
	private static final Logger logger_ = LoggerFactory.getLogger(TraceV3.class);
	private SCTraceabilityVX contract = null;
	private Credentials owner = null;
	private Web3JConnector web3JConnector;

	/**
	 * Constructor with following parameters.
	 * 
	 * @param web3JConnector
	 * @param contractAddress
	 * @param owner
	 * @throws EtherGenericException
	 */
	@SuppressWarnings("deprecation")
	public TraceV3(Web3JConnector web3JConnector, String contractAddress, Credentials owner)
			throws EtherGenericException {
		try {
			this.web3JConnector = web3JConnector;
			contract = SCTraceabilityVX.load(contractAddress, web3JConnector.getWeb3j(), owner,
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
	 * @category createBaseTransaction
	 * @param _id      : Unique identity of the newly created item/virtual object
	 * @param _data    : information related to the item/virtual object
	 * @param _parents : Parent if wants to attach this item/virtual object with
	 *                 others. May be more than one ids seperated by ';'
	 * @param _other   : any other extra information about item/virtual object
	 */
	public TransactionReceipt createBaseTransaction(String _id, String _data, ArrayList<String> _parents, String _other)
			throws EtherGenericException {

		isContractNull();

		try {

			TransactionReceipt tr = contract.newTransaction(_id, _data, new VTUtils().toFormated(_parents), _other)
					.send();
			addTransLink(_id, owner.getAddress(), tr.getTransactionHash());
			return tr;

		} catch (Exception e) {

			logger_.error("Problems when adding transaction !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	@Override
	/**
	 * @category appendTransaction
	 * @param _id       : Unique identity of the newly created item/virtual object
	 * @param _receiver : the recipient of the transaction
	 * @param _data     : information related to the item/virtual object or
	 *                  transaction (these can be evidences)
	 * 
	 */

	public TransactionReceipt appendTransaction(String _id, String _receiver, String _data)
			throws EtherGenericException {

		isContractNull();

		try {

			TransactionReceipt tr = contract.appendTransaction(_id, _receiver, _data).send();
			System.out.println(tr.getStatus());
			addTransLink(_id, _receiver, tr.getTransactionHash());
			return tr;

		} catch (Exception e) {

			logger_.error("Problems when adding transaction !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}
	}

	/**
	 * @category appendTransaction
	 * @param transaction : object of transaction that contains id, receiver and
	 *                    data. please check its twin function appendTransaction for
	 *                    more information
	 * 
	 */
	@Override
	public TransactionReceipt appendTransaction(Transaction transaction) throws EtherGenericException {
		isContractNull();
		try {
			return appendTransaction(transaction.getId(), transaction.getReceiver(), transaction.getTransactionData());

		} catch (Exception e) {
			logger_.error("Problems when adding (param) contract !");
			logger_.error(e.getMessage());
			throw new EtherGenericException(e);
		}
	}

	/**
	 * @category fetchBaseTransaction: This function will return the basic
	 *           transaction along with required information when created by the
	 *           first owner of the item/virtual object.
	 * @param _id : iUnique identity of the item/virtual object to be fetched
	 * 
	 */
	@Override
	public BaseTransaction fetchBaseTransaction(String _id) throws EtherGenericException {
		isContractNull();
		try {
			Tuple5<String, String, String, String, String> t5 = contract.fetchBaseTransaction(_id).send();
			if (Objects.isNull(t5)) {
				logger_.warn("Could not find transaction ! {_id:\" " + _id + "}\"");
				throw new EtherGenericException("Could not find transaction ! {_id:" + _id + "}");
			}
			BaseTransaction transaction = new BaseTransaction(t5.component1(), _id, t5.component3(),
					new VTUtils().fromFormated(t5.component4()), t5.component5());
			return transaction;
		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find transaction ! {_id:\" " + _id + " \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No transaction found !  {_id:\" "+ _id + " \"}\"");
		} catch (Exception e) {
			logger_.error("Problems when fetching transaction ! {_id:" + _id + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching transaction ! {_id:" + _id + "}");
		}
	}

	/**
	 * @category fetchTransaction: This function will return the transaction along
	 *           with required information like id, sender, receiver, data
	 *           associated with transaction.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * @param _no : Specify transaction number to be fetched.
	 * 
	 */
	@Override
	public Transaction fetchTransaction(String _id, long _no) throws EtherGenericException {

		isContractNull();
		if (_no <= 0) {
			logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			throw new EtherGenericException(
					new IndexOutOfBoundsException("Transaction number should be greater than 1 !"));
		}

		try {
			Tuple3<String, String, String> t3 = contract.fetchTransaction(_id, new BigInteger(Long.toString(_no)))
					.send();
			if (Objects.isNull(t3)) {
				logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find transaction ! {_id:" + _id + ", no:" + _no + "}");
			}

			Transaction transaction = new Transaction(_id, t3.component1(), t3.component2(), t3.component3());
			// System.out.println(transaction.toString());
			return transaction;
		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No transaction found ! {_id:" + _id + ", no:" + _no + "}");
		} catch (Exception e) {
			logger_.error("Problems when fetching transaction ! {_id:" + _id + ", no:" + _no + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching transaction ! {_id:" + _id + ", no:" + _no + "}");
		}
	}

	/**
	 * @category get: This function will return complete traceability transactions.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * @param _no : Specify transaction number to be fetched.
	 * 
	 */
	@Override
	public TraceabilityList get(String _id) throws EtherGenericException {
		isContractNull();
		TraceabilityList transactionList = new TraceabilityList();
		BaseTransaction baseTransaction = fetchBaseTransaction(_id);
		transactionList.setId(_id);
		transactionList.setInitiator(baseTransaction.getSender());
		transactionList.setData(baseTransaction.getData());
		transactionList.setParents(baseTransaction.getParents());
		transactionList.setDescription(baseTransaction.getDescription());
		long size = size(_id);

		for (long i = 1; i < size; i++) {
			transactionList.getTransactions().add(fetchTransaction(_id, i));
		}
		return transactionList;
	}

	/**
	 * @category size: This function will return number of transactions against an
	 *           _id.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * 
	 */
	@Override
	public long size(String _id) throws EtherGenericException {
		isContractNull();
		try {
			BigInteger count = contract.getTransactionCountsById(_id).send();
			return count.longValueExact();
		} catch (Exception e) {
			logger_.error("Problems when fetching size of the stack against ! {_id:" + _id + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against ! {_id:" + _id + "}");
		}
	}

	/**
	 * @category totalIds: This function will return number of identities.
	 * 
	 */
	@Override
	public long totalIds() throws EtherGenericException {
		isContractNull();
		try {
			BigInteger count = contract.counts().send();
			return count.longValueExact();
		} catch (Exception e) {
			e.printStackTrace();
			logger_.error("Problems when fetching size of the stack against !");
			logger_.error(e.getMessage());
			throw new EtherGenericException(e);
		}
	}

	/**
	 * @category totalIds: This function will return number of identities.
	 * @param idNo : Specify number to get ID value
	 * 
	 */
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

	/**
	 * @category getTransactionStatus: This function will return transaction status.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * @param _no : Specify transaction number to be fetched.
	 * 
	 */
	@Override
	public TransactionStatus getTransactionStatus(String _id, long _no) throws EtherGenericException {
		isContractNull();

		try {
			Tuple3<String, String, String> t3 = contract.fetchTransaction(_id, new BigInteger(Long.toString(_no)))
					.send();
			if (Objects.isNull(t3)) {
				logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find transaction ! {_id:" + _id + ", no:" + _no + "}");
			}

			Transaction transaction = new Transaction(_id, t3.component1(), t3.component2(), t3.component3());
			TransactionStatus rescordStatus = new TransactionStatus();
			rescordStatus.setId(_id);
			rescordStatus.setNo(_no);
			if (transaction.getReceiver().equals(owner.getAddress())) {
				rescordStatus.setRole(Role.OWNER);
			} else if (transaction.getSender().equals(owner.getAddress())) {
				rescordStatus.setRole(Role.PARTICIPATED);
			} else {
				rescordStatus.setRole(Role.NONE);
			}
			return rescordStatus;
		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No transaction found ! {_id:" + _id + ", no:" + _no + "}");
		} catch (Exception e) {
			logger_.error("Problems when fetching transaction ! {_id:" + _id + ", no:" + _no + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching transaction ! {_id:" + _id + ", no:" + _no + "}");
		}
	}

	/**
	 * @category getTransactionStatus: This function will return
	 *           TransactionStatusList which contains all transaction with their
	 *           status.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * 
	 */
	@Override
	public TransactionStatusList getTransactionStatus(String _id) throws EtherGenericException {
		logger_.debug("In case of problem, please debug 'getRescordStatus with id' function !");
		isContractNull();
		TransactionStatusList transactionStatusList = new TransactionStatusList();
		long size = size(_id);
		for (long i = 0; i < size; i++) {
			transactionStatusList.add(getTransactionStatus(_id, i));
		}
		return transactionStatusList;
	}

	private boolean isContractNull() throws NullPointerException {
		if (Objects.isNull(contract)) {
			logger_.error("Smart contract address is null !");
			throw new NullPointerException("Smart contract address is null !");
		}
		return true;
	}

	/**
	 * @category owner: Returns the owner of the item/virtual object.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * 
	 */
	@Override
	public String owner(String _id) throws EtherGenericException {
		long lastIdNo = size(_id);
		if (lastIdNo == 0) {
			throw new EtherGenericException("No transaction found exception !");

		}
		TransactionStatus transactionStatus = getTransactionStatus(_id, lastIdNo - 1);
		if (transactionStatus.getRole() == Role.OWNER) {
			return this.owner.getAddress();
		} else {
			throw new EtherGenericException("You are not the owner of '" + _id + "' !");
		}
	}

	/**
	 * @category ownedTransactionStatus: Returns the status of the owner transaction
	 *           of the item/virtual object.
	 * @param _id : Unique identity of the item/virtual object to be fetched
	 * 
	 */
	public TransactionStatus ownedTransactionStatus(String _id) throws EtherGenericException {
		long lastIdNo = size(_id);
		if (lastIdNo != 0) {
			TransactionStatus transactionStatus = getTransactionStatus(_id, lastIdNo - 1);
			if (transactionStatus.getRole() == Role.OWNER) {
				return transactionStatus;
			} else {
				return null;
			}
		} else {
			throw new EtherGenericException("Nor transaction found exception !");
		}
	}

	/**
	 * @category getTransactionStatusOwnedByMe: Returns the complete list of
	 *           identities owned by me.
	 * 
	 */
	@Override
	public TransactionStatusList getTransactionStatusOwnedByMe() throws TraceabilityException, EtherGenericException {
		long l = totalIds();
		TransactionStatusList transactionStatusList = new TransactionStatusList();
		for (long b = 0; b < l; b++) {
			String id = getIdString(b);
			TransactionStatus transactionStatus = ownedTransactionStatus(id);
			if (!Objects.isNull(transactionStatus))
				transactionStatusList.add(transactionStatus);
		}
		return transactionStatusList;
	}

	/**
	 * @category getTransactionStatusOfAllIds: Returns the status of the complete
	 *           list of the identities.
	 * 
	 */
	@Override
	public TransactionStatusList getTransactionStatusOfAllIds() throws TraceabilityException, EtherGenericException {

		long l = totalIds();
		TransactionStatusList transactionStatusList = new TransactionStatusList();
		for (long b = 0; b < l; b++) {
			String id = getIdString(b);
			long idSize = size(id);
			if (idSize != 0) {
				TransactionStatus transactionStatus = getTransactionStatus(id, idSize - 1);
				transactionStatusList.add(transactionStatus);
			}
		}
		return transactionStatusList;
	}

	/**
	 * @category geTotalTransLinkById : return blockchain address of all transaction
	 *           perform on this item/virtual object
	 * 
	 * @param _id : Unique identity of the item/virtual object
	 * @throws EtherGenericException
	 */
	@Override
	public long geTotalTransLinkById(String _id) throws EtherGenericException {

		try {
			BigInteger count = contract.geTotalTransLinkById(_id).send();
			return count.longValueExact();
		} catch (Exception e) {
			logger_.error("Problems when fetching size of the stack against ! {_id:" + _id + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching size of the stack against ! {_id:" + _id + "}");
		}

	}

	/**
	 * @category geTotalTransLinkById : return blockchain address of all transaction
	 *           perform on this item/virtual object
	 * 
	 * @param _id        : Unique identity of the item/virtual object
	 * @param _receiver  : The receiver of the transaction
	 * @param transHashs : transaction address
	 * @throws EtherGenericException
	 */
	@Override
	public TransactionReceipt addTransLink(String _id, String _receiver, String transHashs)
			throws EtherGenericException {
		isContractNull();

		try {

			return contract.addTransLink(_id, _receiver, transHashs).send();

		} catch (Exception e) {

			logger_.error("Problems when adding transaction !");
			logger_.error(e.getMessage());

			throw new EtherGenericException(e);
		}

	}

	/**
	 * @category getTransLink : return blockchain transaction address of
	 *           item/virtual object of specific transaction number
	 * @param web3JConnector  : object of web3 connector
	 * @param contractAddress : smart contract address
	 * @param _id             : Unique identity of the item/virtual object
	 * @param tno             : transaction number
	 * @throws EtherGenericException
	 */
	@Override
	public String getTransLink(String _id, long tno) throws EtherGenericException {
		isContractNull();
		try {
			Tuple2<String, String> t2 = contract.getTransLink(_id, new BigInteger(Long.toString(tno))).send();
			if (Objects.isNull(t2)) {
				logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
				throw new EtherGenericException("Could not find transaction ! {_id:" + _id + ", no:" + tno + "}");
			}

			return t2.component2();

		} catch (IndexOutOfBoundsException aiob) {
			logger_.warn("Could not find transaction ! {_id:\" + _id + \", no:\" + _no + \"}\"");
			logger_.warn(aiob.getMessage());
			throw new NoRecordFoundException("No transaction found ! {_id:" + _id + ", no:" + tno + "}");
		} catch (Exception e) {
			logger_.error("Problems when fetching transaction ! {_id:" + _id + ", no:" + tno + "}");
			logger_.error(e.getMessage());
			throw new EtherGenericException("Problems when fetching transaction ! {_id:" + _id + ", no:" + tno + "}");
		}
	}

	/**
	 * @category getSCTraceabilityVX : return smart contract object
	 */
	@Override
	public SCTraceabilityVX getSCTraceabilityVX() throws Exception {
		return contract;
	}

	/**
	 * @category getBlockNumber : returns ethereum blockchain last block number
	 *           smart contract object
	 */
	public EthBlockNumber getBlockNumber() throws Exception {
		EthBlockNumber result = new EthBlockNumber();
		result = web3JConnector.getWeb3j().ethBlockNumber().sendAsync().get();
		return result;
	}

	private ArrayList<BaseTransaction> getParentGraph(String id, ArrayList<BaseTransaction> parents, int init,
			int height) throws Exception {

		if (height == init) {
			return parents;
		}
		init += 1;

		BaseTransaction baseTransaction = null;
		try {
			baseTransaction = fetchBaseTransaction(id);
			parents.add(baseTransaction);
			for (String p : baseTransaction.getParents()) {
				getParentGraph(p, parents, init, height);
			}
		} catch (Exception exp) {

		}
		return parents;
	}

	public void getParentGraph(String id, int height) throws Exception {
		ArrayList<BaseTransaction> parents = new ArrayList<BaseTransaction>();
		parents = getParentGraph(id, parents, 0, height);
		System.out.println(parents.size());
		new VTUtils().parentGraph(parents);

	}

	public void getTransactionalGraph(String id) throws Exception {
		BaseTransaction baseTransaction = fetchBaseTransaction(id);
		new VTUtils().transactionGraph(get(id));
	}
	
	public boolean isSmartContractExists() {
		try {
			totalIds();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
