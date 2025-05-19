package centralhostregistration.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import com.fasterxml.jackson.databind.ObjectMapper;

import centralhostregistration.dto.ConfigUnit;
import centralhostregistration.utils.ecaccts.ECAccount;
import centralhostregistration.utils.ecaccts.ECAccountList;
import centralhostregistration.utils.ecaccts.KeyStoreParser;

public class ManageOthers {

	public void manageKeys(String storeFilePath) throws IOException {
		//Path bool = Files.write(Paths.get(cu.getKey()), cu.getValue().getBytes());
		ECAccountList h = new KeyStoreParser(new File(storeFilePath).getAbsolutePath()).get();
		ObjectMapper objectMapper = new ObjectMapper();
		String serECAccountList = objectMapper.writeValueAsString(h);
		Path bool = Files.write(Paths.get("arg_store.txt"), serECAccountList.getBytes());

	}

	public ECAccount getKey() throws IOException {
		byte[] data = Files.readAllBytes(Paths.get("arg_store.txt"));
		ObjectMapper objectMapper = new ObjectMapper();
		ECAccountList list = objectMapper.readValue(data, ECAccountList.class);
		Enumeration<String> keys = list.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (list.get(key).isAvailable()) {
				return list.get(key);
			}
		}
		return null;
	}
	
	public ECAccount keyConsumed(String key) throws IOException {
		byte[] data = Files.readAllBytes(Paths.get("arg_store.txt"));
		ObjectMapper objectMapper = new ObjectMapper();
		ECAccountList list = objectMapper.readValue(data, ECAccountList.class);
		ECAccount ec = list.get(key);
		ec.setAvailable(false);
		list.put(key, ec);
		String serECAccountList = objectMapper.writeValueAsString(list);
		Files.deleteIfExists(Paths.get("arg_store.txt"));
		Files.write(Paths.get("arg_store.txt"), serECAccountList.getBytes());
		return ec;
	}
	

	public ECAccountList getKeys() throws IOException {
		byte[] data = Files.readAllBytes(Paths.get("arg_store.txt"));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(data, ECAccountList.class);
		
	}
}
