package centralhostregistration.utils.ecaccts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class KeyStoreParser {

	private ECAccountList ecAccountList = new ECAccountList();
	public KeyStoreParser(String file) {
		parse(file);
		
	}
	private void parse(String file) {
		
		try {
			try (Stream<String> stream = Files.lines(Paths.get(file))) {
				int previous = 0;
				stream.filter(s -> s.startsWith("(")).forEach(s -> {
					int index = s.indexOf(")");
					if (index != -1) {
						String i = s.substring(1, index);
						if (ecAccountList.containsKey(i)) {
							String key = s.substring(index + 1).trim();
							ECAccount ecaccount = ecAccountList.get(i);
							ecaccount.setPrivateKey(key);
							ecaccount.setKeyNo(i);
							ecaccount.setAvailable(true);
							ecAccountList.put(i, ecaccount);
						} else {
							int zIn = s.indexOf(" ");
							String temp = s.substring(zIn);
							temp = temp.trim();
							int nindex = temp.indexOf(" ");
							String key = temp.substring(0, nindex).trim();
							ECAccount ecaccount = new ECAccount();
							ecaccount.setPublicKey(key);
							ecAccountList.put(i, ecaccount);
						}
					}
				});
			}
			
		} catch (IOException io) {
			io.printStackTrace();
			ecAccountList = null;
		}
		
	}
	public ECAccount get(int index) {
		return ecAccountList.get(""+index);
	}
	
	public ECAccountList get() {
		return ecAccountList;
	}
	public static void main(String arg[]) {
		String file = "/home/ag/Desktop/RISE/restout/centralhostregistration/store.txt";
		new KeyStoreParser(file);
	}

}
