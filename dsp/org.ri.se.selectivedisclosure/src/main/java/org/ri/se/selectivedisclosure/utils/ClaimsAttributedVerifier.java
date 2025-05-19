package org.ri.se.selectivedisclosure.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.acreo.security.processor.CryptoProcessor;
import org.ri.se.selectivedisclosure.ExtendedKeyValue;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClaimsAttributedVerifier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public boolean verifyAttribute(Map<String, Object> claimsData, Map<String, Object> digest) throws Exception {
		return processAttributes(claimsData, digest);
	}
	
	private boolean verifyAttributes(String attributeName, Object attribute, String hahsValue) throws Exception {
		CryptoProcessor cryptoProcessor = new CryptoProcessor();
		ExtendedKeyValue ekv = new ExtendedKeyValue();
		ekv.deserialize(new ObjectMapper().writeValueAsString(attribute));
		if(ekv.getValue()==null)
			ekv.setValue("");
		if (!cryptoProcessor.verify(ekv.serialize().getBytes(), hahsValue.getBytes())) {
			throw new Exception("Verification of claims failed !");
		}
		return true;
	}

	public boolean processAttributes(Map<String, Object> claimsData, Map<String, Object> digest) throws Exception {
	//	JSONObject json = new JSONObject(claimsData);
		List sortedKeysJson = new ArrayList(claimsData.keySet());
		for (int i = 0; i < sortedKeysJson.size(); i++) {
			String attribute = (String) sortedKeysJson.get(i);
			Object obj = claimsData.get(attribute);
			if (digest.containsKey(attribute)) {
				ExtendedKeyValue ekv = new ExtendedKeyValue();
				try {
					ObjectMapper om = new ObjectMapper();
					ekv = om.readValue(om.writeValueAsString(obj), ExtendedKeyValue.class);					
				} catch (Exception e) {
					System.err.println("Invalid construction of verifiable attribute !");
					System.err.println(e.getMessage());
					return false;
				}
				
				verifyAttributes(attribute, obj, digest.get(attribute).toString());

			} else {
				ExtendedKeyValue ekv;
				try {
					ObjectMapper om = new ObjectMapper();
					ekv = om.readValue(om.writeValueAsString(obj), ExtendedKeyValue.class);
					if (!Objects.isNull(ekv)) {
						if (!(Objects.isNull(ekv.getName()) && Objects.isNull(ekv.getValue())
								&& Objects.isNull(ekv.getSalt()))) {
							System.err.println("Invalid verifiable attribute or verfiable attribute '" + attribute
									+ "' does not exist !!");
							return false;
						}
					}
				} catch (Exception e) {

				}
			}
			// ======================================
			if (obj instanceof ArrayList) {
				ArrayList<Object> o = (ArrayList) obj;
				manageArray(attribute, o, digest);
			} else if (obj instanceof Map) {
				Map<String, Object> claimsData2 = (Map<String, Object>) obj;
				processAttributes(claimsData2, digest);
			}
		}
		return true;
	}

	public boolean manageArray(String key, ArrayList<Object> o, Map<String, Object> digest) throws Exception {
		for (int i = 0; i < o.size(); i++) {

			if (o.get(i) instanceof ArrayList) {
				manageArray(key, (ArrayList) o.get(i), digest);
			} else if (o.get(i) instanceof Map) {
				Map<String, Object> claimsData2 = (Map<String, Object>) o.get(i);
				processAttributes(claimsData2, digest);
			}
		}
		return true;
	}
}
