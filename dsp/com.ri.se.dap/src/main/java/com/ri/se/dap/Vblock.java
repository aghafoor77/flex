package com.ri.se.dap;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.11.
 */
@SuppressWarnings("rawtypes")
public class Vblock extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611180806100616000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c806376f456c21161007157806376f456c21461040d5780638da5cb5b14610508578063bb9c6c3e14610552578063bff1f9e114610625578063e48e7a8114610643578063eb68048b14610712576100a9565b806322a72bff146100ae57806324d7806c146101a957806341148dbe14610205578063453b1442146102f45780636646964a14610350575b600080fd5b610167600480360360208110156100c457600080fd5b81019080803590602001906401000000008111156100e157600080fd5b8201836020820111156100f357600080fd5b8035906020019184600183028401116401000000008311171561011557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610730565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101eb600480360360208110156101bf57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506107c6565b604051808215151515815260200191505060405180910390f35b6102de6004803603604081101561021b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291908035906020019064010000000081111561025857600080fd5b82018360208201111561026a57600080fd5b8035906020019184600183028401116401000000008311171561028c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610826565b6040518082815260200191505060405180910390f35b6103366004803603602081101561030a57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610cea565b604051808215151515815260200191505060405180910390f35b6103926004803603602081101561036657600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610e03565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156103d25780820151818401526020810190506103b7565b50505050905090810190601f1680156103ff5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6104c66004803603602081101561042357600080fd5b810190808035906020019064010000000081111561044057600080fd5b82018360208201111561045257600080fd5b8035906020019184600183028401116401000000008311171561047457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610ee7565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610510610f7d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61060b6004803603602081101561056857600080fd5b810190808035906020019064010000000081111561058557600080fd5b82018360208201111561059757600080fd5b803590602001918460018302840111640100000000831117156105b957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610fa7565b604051808215151515815260200191505060405180910390f35b61062d611031565b6040518082815260200191505060405180910390f35b6106fc6004803603602081101561065957600080fd5b810190808035906020019064010000000081111561067657600080fd5b82018360208201111561068857600080fd5b803590602001918460018302840111640100000000831117156106aa57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929050505061103a565b6040518082815260200191505060405180910390f35b61071a61109c565b6040518082815260200191505060405180910390f35b60006002826040518082805190602001908083835b602083106107685780518252602082019150602081019050602083039250610745565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60006001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154141561081c5760019050610821565b600090505b919050565b600060016002836040518082805190602001908083835b60208310610860578051825260208201915060208101905060208303925061083d565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600201541415610942577fc0089fbf2e0ee14d29fd81c7fb2347d1157d316f6be7a41ec9aea11a11f48bfb83604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825260148152602001807f416c7265616479207265676973746572656420210000000000000000000000008152506020019250505060405180910390a160009050610ce4565b6001600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001541415610c4357826002836040518082805190602001908083835b602083106109c457805182526020820191506020810190506020830392506109a1565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550336002836040518082805190602001908083835b60208310610a6f5780518252602082019150602081019050602083039250610a4c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060016002836040518082805190602001908083835b60208310610b1b5780518252602082019150602081019050602083039250610af8565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206002018190555060008081548092919060010191905055507fc0089fbf2e0ee14d29fd81c7fb2347d1157d316f6be7a41ec9aea11a11f48bfb8383604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610bfb578082015181840152602081019050610be0565b50505050905090810190601f168015610c285780820380516001836020036101000a031916815260200191505b50935050505060405180910390a16001600054039050610ce4565b7fc0089fbf2e0ee14d29fd81c7fb2347d1157d316f6be7a41ec9aea11a11f48bfb83604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001806020018281038252600c8152602001807f4e6f7420616e2041646d696e00000000000000000000000000000000000000008152506020019250505060405180910390a1600090505b92915050565b60006001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001541415610d405760019050610dfe565b3373ffffffffffffffffffffffffffffffffffffffff16600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610df9576001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000018190555060056000815480929190600101919050555060019050610dfe565b600090505b919050565b6060600460008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610edb5780601f10610eb057610100808354040283529160200191610edb565b820191906000526020600020905b815481529060010190602001808311610ebe57829003601f168201915b50505050509050919050565b60006002826040518082805190602001908083835b60208310610f1f5780518252602082019150602081019050602083039250610efc565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6000600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b600060016002836040518082805190602001908083835b60208310610fe15780518252602082019150602081019050602083039250610fbe565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020600201541415611027576001905061102c565b600090505b919050565b60008054905090565b600081600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000190805190602001906110929291906110a6565b5060009050919050565b6000600554905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110e757805160ff1916838001178555611115565b82800160010185558215611115579182015b828111156111145782518255916020019190600101906110f9565b5b5090506111229190611126565b5090565b61114891905b8082111561114457600081600090555060010161112c565b5090565b9056fea265627a7a723158202bfa892526dc72477f0aff896f146adc6ce5d5e1008894f466ab603f2cfae1e964736f6c634300050c0032";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GIVEUSERRIGHTS = "giveUserRights";

    public static final String FUNC_VERIFY = "verify";

    public static final String FUNC_PUBLICKEY = "publickey";

    public static final String FUNC_FINDADMINOFUSER = "findAdminOfUser";

    public static final String FUNC_TOTALUSERS = "totalUsers";

    public static final String FUNC_ISADMIN = "isAdmin";

    public static final String FUNC_GIVEADMINRIGHTS = "giveAdminRights";

    public static final String FUNC_TOTALADMINS = "totalAdmins";

    public static final String FUNC_ADDRSAKEY = "addRSAKey";

    public static final String FUNC_GETRSAKEY = "getRSAKey";

    public static final Event REGISTERUSER_EVENT = new Event("RegisterUser", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Vblock(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Vblock(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Vblock(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Vblock(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<RegisterUserEventResponse> getRegisterUserEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTERUSER_EVENT, transactionReceipt);
        ArrayList<RegisterUserEventResponse> responses = new ArrayList<RegisterUserEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterUserEventResponse typedResponse = new RegisterUserEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.userAdd = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.tok = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegisterUserEventResponse> registerUserEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RegisterUserEventResponse>() {
            @Override
            public RegisterUserEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTERUSER_EVENT, log);
                RegisterUserEventResponse typedResponse = new RegisterUserEventResponse();
                typedResponse.log = log;
                typedResponse.userAdd = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.tok = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegisterUserEventResponse> registerUserEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTERUSER_EVENT));
        return registerUserEventFlowable(filter);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> giveUserRights(String userAddress, String tok) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GIVEUSERRIGHTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userAddress), 
                new org.web3j.abi.datatypes.Utf8String(tok)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verify(String tok) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(tok)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> publickey(String tok) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(tok)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> findAdminOfUser(String tok) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FINDADMINOFUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(tok)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalUsers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALUSERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isAdmin(String adminA) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(adminA)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> giveAdminRights(String adminAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GIVEADMINRIGHTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(adminAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalAdmins() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addRSAKey(String pkVal) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDRSAKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(pkVal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getRSAKey(String useraddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRSAKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(useraddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Vblock load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Vblock(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Vblock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Vblock(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Vblock load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Vblock(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Vblock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Vblock(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Vblock> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Vblock.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Vblock> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Vblock.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Vblock> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Vblock.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Vblock> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Vblock.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class RegisterUserEventResponse extends BaseEventResponse {
        public String userAdd;
        public String tok;
    }
}
