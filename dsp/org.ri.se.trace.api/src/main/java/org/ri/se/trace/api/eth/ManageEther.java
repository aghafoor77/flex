package org.ri.se.trace.api.eth;

import java.util.List;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.contract.ContractDeployer;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert.Unit;

public class ManageEther {

	private static Logger logger_ = Logger.getLogger(ManageEther.class);
	public ManageEther() {
		
	}
	private static Web3j getWeb3j(String url) throws Exception {
		if (Objects.isNull(url)) {
			System.err.print("URL is not defined, please call constructor once before geting web3j instance !");
			throw new Exception("URL is not defined, please call constructor once before geting web3j instance !");
		}
		return Web3j.build(new HttpService(url));

	}

	public static void main(String[] args) throws Exception {
		//GanacheIdentity exampleIdentity = new GanacheAccountData("/home/blockchain/eth/store.txt").getExampleIdentity();
		
		//String password = "1122334455";
		//String etherURL = "http://localhost:8545";
		
		/*
		 * new EtherTransferManager().getEthBalance( getWeb3j("http://172.17.0.2:8545"),
		 * "0x2Ef3cE6BB85A068667FD83810A1D4795bEa2BBfD");
		 * 
		 * boolean t = true; if(t) { return; }
		 */
		
		ManageEther manageEther = new ManageEther();
		
		
		EtherProps etherProps = new EtherProps();
		String[] optionsStr = { "Manage Balanace and Deploy Contract", "Manage Balance", "Deploy Contract" };

		JComboBox jcd = new JComboBox(optionsStr);
		jcd.setEditable(false);
		String walletDir = etherProps.getCredentialsPath();
		// create a JOptionPane
		Object[] options = new Object[] {};
		String option = JOptionPane.showInputDialog(null, "Please slection options ?", "Smart Contract Manager",
				JOptionPane.QUESTION_MESSAGE, null, optionsStr, optionsStr[0]).toString();
		if (option.equalsIgnoreCase(optionsStr[0])) {
			manageEther.manageAccounts(0, "9000000000", walletDir, etherProps.getUsername(), etherProps.getPassword(), etherProps.getEtherURL());
			ContractDeployer contractDeployer = new ContractDeployer(etherProps.getEtherURL());
			String smartContractAddress = contractDeployer.deployTraceabilityContract(walletDir, etherProps.getUsername(), etherProps.getPassword());
			System.out.println("This is smart contract address : "+smartContractAddress );
			manageEther.displayMyBalanace(walletDir, etherProps.getUsername(), etherProps.getPassword(), etherProps.getEtherURL());
		} else if (option.equalsIgnoreCase(optionsStr[1])) {
			manageEther.manageAccounts(0, "9000000000", walletDir, etherProps.getUsername(), etherProps.getPassword(), etherProps.getEtherURL());
		} else {
			if (option.equalsIgnoreCase(optionsStr[2])) {
				ContractDeployer contractDeployer = new ContractDeployer(etherProps.getEtherURL());
				String smartContractAddress = contractDeployer.deployTraceabilityContract(walletDir, etherProps.getUsername(), etherProps.getPassword());
				System.out.println("This is smart contract address : "+smartContractAddress );
				manageEther.displayMyBalanace(walletDir, etherProps.getUsername(), etherProps.getPassword(), etherProps.getEtherURL());
			}
		}
	}

	public void displayMyBalanace(String walletDir, String username, String password, String etherURL) throws Exception {
		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);
		double bal = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), cred.getAddress());
		System.out.printf("Account (%s) balanace = %.0f %s\n", cred.getAddress(), bal, Unit.ETHER.toString());
	}
	
	public boolean manageAccounts(int accountIndex, String money, String walletDir, String username, String password,String etherURL) throws Exception {
		GanacheIdentity exampleIdentity = new GanacheAccountData(new EtherProps().getStorePath()).getExampleIdentity(accountIndex);
		String privateKey = exampleIdentity.getPrivateKeyToBorrowMoney();// "0x5fee56d55e4909b1e6e39eb28228af1873b70a55c089e341d7cbcfed9f5486d7";
		String contractAddress = null;
		 
		// Fetch Accounts from ganache-cli
		EthAccounts eaccts = new AccountsManager().getEthAccounts( getWeb3j(etherURL));
		List<String> aaa = eaccts.getAccounts();

		if(accountIndex+1 > aaa.size()) {
			throw new Exception("Account does not exisits. All accounts are consumed !");
		}
		System.out.println("Total default accounts of ganash : " + aaa.size());
		// Create my own local accound
		System.out.println("Creating a new account !");
		String address = new AccountsManager().createAccount(walletDir, username,  password);
		System.out.println("Address of newly created account is = " + address);
		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);
		
		double bal = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), cred.getAddress());
		System.out.printf("Account (%s) balanace = %.0f %s\n", address, bal, Unit.ETHER.toString());
		
		Credentials credSender = new AccountsManager().createCredentilsFromPrivateKey(privateKey);
		double balSender = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), credSender.getAddress());
		System.out.printf("Account (%s) balanace = %.0f %s\n", credSender.getAddress(), balSender,
				Unit.ETHER.toString());

		try {
			System.out.println("Transferring "+money+" " + Unit.ETHER.toString());
			System.out.println("\tfrom " + credSender.getAddress());
			System.out.println("\tto  " + cred.getAddress() + " account ... ");
			new EtherTransferManager().transferEther( getWeb3j(etherURL), privateKey, cred.getAddress(), money/*"4500000000"*/, Unit.ETHER,
					"1", Unit.GWEI);
			bal = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), cred.getAddress());
			System.out.printf("Updated account (%s) balanace = %.0f %s\n", address, bal, Unit.ETHER.toString());
			return true;
		} catch (Exception ibe) {
			
			if(ibe.getMessage().startsWith("Insuficent balance in")) {
				System.err.println("Trying another account !");
				accountIndex++;
				manageAccounts(accountIndex, money, walletDir,username,  password, etherURL);
			}				
			return false;
		}
	}	
	
	public boolean manageAccounts(String privateKey, String money, String walletDir, String username, String password,String etherURL) throws Exception {
		String contractAddress = null;
		 
		// Create my own local accound
		
		Credentials cred = new AccountsManager().getCredentials(walletDir, username, password);
		System.out.println("Address = " + cred.getAddress());
		System.out.println(etherURL);
		double bal = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), cred.getAddress());
		System.out.printf("Account (%s) balanace = %.0f %s\n", cred.getAddress(), bal, Unit.ETHER.toString());
		
		Credentials credSender = new AccountsManager().createCredentilsFromPrivateKey(privateKey);
		double balSender = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), credSender.getAddress());
		System.out.printf("Account (%s) balanace = %.0f %s\n", credSender.getAddress(), balSender,
				Unit.ETHER.toString());

		try {
			System.out.println("Transferring "+money+" " + Unit.ETHER.toString());
			System.out.println("\tfrom " + credSender.getAddress());
			System.out.println("\tto  " + cred.getAddress() + " account ... ");
			new EtherTransferManager().transferEther( getWeb3j(etherURL), privateKey, cred.getAddress(), money/*"4500000000"*/, Unit.ETHER,
					"1", Unit.GWEI);
			bal = new EtherTransferManager().getEthBalance( getWeb3j(etherURL), cred.getAddress());
			System.out.printf("Updated account (%s) balanace = %.0f %s\n", cred.getAddress(), bal, Unit.ETHER.toString());
			return true;
		} catch (Exception ibe) {
			ibe.printStackTrace();
			return false;
		}
	}	
	
}
