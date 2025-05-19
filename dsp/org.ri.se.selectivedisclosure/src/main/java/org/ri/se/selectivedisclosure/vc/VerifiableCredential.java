package org.ri.se.selectivedisclosure.vc;

import java.util.Map;
import java.util.Objects;

import org.acreo.security.exceptions.VeidblockException;
import org.ri.se.selectivedisclosure.ExtendedKeyValueList;
import org.ri.se.selectivedisclosure.utils.Utils;
import org.ri.se.verifiablecredentials.asymmetric.RSA2018VerifiableCredentials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

import foundation.identity.jsonld.JsonLDObject;

public class VerifiableCredential {

	private JsonLDObject jsonObj = null;

	public VerifiableCredential(String json) {
		super();
		jsonObj = JsonLDObject.fromJson(json);
	}

	public boolean verifyOffline() throws Exception {
		return new RSA2018VerifiableCredentials().verifyOffline(jsonObj.toJson(true));
	}

	public boolean verifyClaimsAttributes() throws Exception {
		if (!new RSA2018VerifiableCredentials().verifyOffline(jsonObj.toJson(true))) {
			return false;
		}
		Map<String, Object> sub = (Map<String, Object>) this.getJsonObject().get(VC.CLAIMS);
		if (Objects.isNull(sub)) {
			throw new Exception("Claims not found !");
		}
		Object subObj = sub.get(VC.ENCRYPTEDCLAIMS);
		if (!Objects.isNull(subObj)) {
			throw new Exception("Protected claims cannot be verified !");
		}
		VerifiableCredentialManager manager = new VerifiableCredentialManager();
		manager.verifyClaimsAttributes(sub, (Map<String, Object>) this.getJsonObject().get(VC.DIGEST_SET));
		return true;
	}

	public boolean verifyOnline(byte[] publickey) throws Exception {
		return new RSA2018VerifiableCredentials().verifyOnline(jsonObj.toJson(true), publickey);
	}

	public Map<String, Object> getClaims() throws Exception {
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> claims = rsaVerifiableCredentials.getClaims(this.toJson());
		Object objEnc = claims.get(VC.ENCRYPTEDCLAIMS);
		if (!Objects.isNull(objEnc)) {
			throw new Exception("Claims are protected !");
		}

		return claims;
	}

	public Map<String, Object> getMettaMapper() throws Exception {
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> metaMapper = rsaVerifiableCredentials.getMetaMappewClaims(this.toJson());
		if (Objects.isNull(metaMapper)) {
			throw new Exception("Meta mapper not found !");
		}
		return metaMapper;
	}
	
	public Map<String, Object> getVCHeaderData() throws Exception {
		Map<String, Object> map = jsonObj.toMap();
		return map;		
	}
	

	public boolean valid() {
		Map<String, Object> objs = jsonObj.getJsonObject();
		if(!objs.containsKey("expirationDate")) {
			return true;
		}
		Object expDate = objs.get("expirationDate");
		System.out.println(expDate.toString() );
		if(!Objects.isNull(expDate)) {
			try {
				new Utils().checkExpiryDate(expDate.toString());
				return true;
			} catch (VeidblockException e) {
				return false;
			}
		}
		return true;
	}

	public ExtendedKeyValueList getClaims(byte[] key) throws Exception {
		return new Utils().extractSecureClaims(jsonObj.toJson(true), key);
	}

	public VerifiableCredential open(byte[] key) throws Exception {
		return new VerifiableCredential(new Utils().vcWithClaims(toJson(), key));
	}

	public String toJson() {
		return jsonObj.toJson(true);
	}

	public String toString() {
		return jsonObj.toJson(true);
	}

	public Map<String, Object> getJsonObject() {
		return jsonObj.getJsonObject();
	}

	public JsonLDObject get() {
		return jsonObj;
	}
}