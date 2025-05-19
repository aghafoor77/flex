package org.ri.se.trace.api.eth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

public class GanacheAccountData {

	public Hashtable<String, String> publicKey = new Hashtable<String, String>();
	public Hashtable<String, String> privateKey = new Hashtable<String, String>();

	public static void main(String[] args) throws IOException {
	}

	public GanacheAccountData(String file) {
		System.out.println(file);
		try {
			try (Stream<String> stream = Files.lines(Paths.get(file))) {
				int previous = 0;
				stream.filter(s -> s.startsWith("(")).forEach(s -> {
					int index = s.indexOf(")");
					if (index != -1) {
						String i = s.substring(1, index);
						if (publicKey.containsKey(i)) {
							String key = s.substring(index + 1).trim();
							privateKey.put(i, key);
						} else {
							int zIn = s.indexOf(" ");
							String temp = s.substring(zIn);
							temp = temp.trim();
							int nindex = temp.indexOf(" ");
							String key = temp.substring(0, nindex).trim();
							publicKey.put(i, key);
						}
					}
				});
			}
			System.out.println(publicKey.get("0"));
			System.out.println(privateKey.get("0"));
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public GanacheIdentity getExampleIdentity(int number) {

		GanacheIdentity exampleIdentity = new GanacheIdentity();
		exampleIdentity.setPrivateKeyToBorrowMoney(privateKey.get(number+""));
		return exampleIdentity;
	}
	
	public String [] getRestAddresses() {
		String[] other = new String[6];
		for(int i=4; i < 10; i++) {
			other [i-4] = publicKey.get(i+"");
		}
		return other;
	}
	
	public void ddd(){
		
		GanacheIdentity exampleIdentity = getExampleIdentity(0);
		
		//Manually copied private key from ganache (account 0)
		String privateKeyToBorrowMoney = exampleIdentity.getPrivateKeyToBorrowMoney() ;
		
		//Manually copied public key (Account 1) from ganache
		String admin = exampleIdentity.getAdmin() ;
		
		//Manually copied private key (Account 1) from ganache
		String privateKyeOfAdmin = exampleIdentity.getPrivateKyeOfAdmin() ;
		
		//Manually copied public key (Account 3) from ganache
		String userAddress = exampleIdentity.getUserAddress() ;
		
		//Manually copied private key (Last Account) from ganache
		String otherPrivateKey = exampleIdentity.getOtherPrivateKey();
		
		String token = "1111222233334444555566667777888811112222333344445555666677778888111122223333444455556666777788881111222233334444555566667777888811112222333344445555666677778888111122223333444455556666777788881111222233334444555566667777888811112222333344445555666677778888";
		
	}
}