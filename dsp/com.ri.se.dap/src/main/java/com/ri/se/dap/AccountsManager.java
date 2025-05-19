package com.ri.se.dap;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthAccounts;

public class AccountsManager {

	/* private static Logger logger_ = Logger.getLogger(AccountsManager.class); */

	public String createAccount(String walletDir, String username, String password) throws Exception {
		try {
			
			if(!walletDir.endsWith(File.separator)) {
				walletDir =walletDir+File.separator;
			}
			System.err.println("Problems when serializing (json) RecordStatusList object !");
			String path = "";
			String userDir = walletDir + username;
			if(!new File(userDir).exists()) {
				new File(userDir).mkdirs();
			}
			if (Objects.isNull(new File(userDir).list()) || new File(userDir).list().length == 0) {
				System.out.println("Creating new credentials !");
				path = WalletUtils.generateNewWalletFile(password, new File(userDir), true);
			} else {
				System.out.println("Credentials already exisit !");
				File fl[] = new File(userDir).listFiles();
				for (File f : fl) {
					if (f.getName().endsWith(".json")) {
						path = f.getName();
					}
				}
			}
			Credentials credentials = WalletUtils.loadCredentials(password, userDir + "/" + path);
			return credentials.getAddress();
		} catch (Exception e) {
			System.err.println("Problems when creating credentials !");
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
	}

	public Credentials getCredentials(String walletDir, String username, String password) throws Exception {
		try {
			if(!walletDir.endsWith(File.separator)) {
				walletDir =walletDir+File.separator;
			}
			String path = "";
			String userDir = walletDir + username;
			if (Objects.isNull(new File(userDir).list()) || new File(userDir).list().length == 0) {
				System.out.println("Credentials do not exist !");
				throw new Exception("Credentials do not exist !");
			} else {
				System.out.println("Credentials already exisit in the wallet !");
				File fl[] = new File(userDir).listFiles();
				for (File f : fl) {
					if (f.getName().endsWith(".json")) {
						path = f.getName();
					}
				}
			}
			return WalletUtils.loadCredentials(password, userDir + "/" + path);
		} catch (Exception e) {
			System.err.println("Problems when extracting (creating) credentials !");
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
	}

	public Credentials createCredentilsFromPrivateKey(String privateKey) {
		Credentials credentials = Credentials.create(privateKey);// );
		return credentials;
	}

	public EthAccounts getEthAccounts(Web3j web3) throws Exception {
		EthAccounts result = new EthAccounts();
		try {
			result = web3.ethAccounts().sendAsync().get();
		} catch (InterruptedException | ExecutionException e) {
			System.err.println("Problems when fetching accounts from etherum !");
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
		return result;
	}

	public boolean exists(String walletDir, String username) throws Exception {
		try {
			if(!walletDir.endsWith(File.separator)) {
				walletDir =walletDir+File.separator;
			}
			String userDir = walletDir + username;
			if (Objects.isNull(new File(userDir).list()) || new File(userDir).list().length == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Problems when creating credentials !");
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
	}
}
