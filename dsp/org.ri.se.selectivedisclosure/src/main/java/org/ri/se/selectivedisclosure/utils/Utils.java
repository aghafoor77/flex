package org.ri.se.selectivedisclosure.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.acreo.security.bc.CertificateHandlingBC;
import org.acreo.security.certificate.CertificateSuite;
import org.acreo.security.crypto.CryptoStructure.ENCODING_DECODING_SCHEME;
import org.acreo.security.exceptions.VeidblockException;
import org.acreo.security.processor.CryptoProcessor;
import org.acreo.security.processor.SecurityContext;
import org.acreo.security.utils.DistinguishName;
import org.acreo.security.utils.StoreHandling;
import org.ri.se.selectivedisclosure.ExtendedKeyValue;
import org.ri.se.selectivedisclosure.ExtendedKeyValueList;
import org.ri.se.selectivedisclosure.RSAAsymmetricKeyPair;
import org.ri.se.selectivedisclosure.SecureClaims;
import org.ri.se.selectivedisclosure.vc.VC;
import org.ri.se.selectivedisclosure.vc.VerifiableCredential;
import org.ri.se.selectivedisclosure.vp.VerifiablePresentation;
import org.ri.se.verifiablecredentials.asymmetric.RSA2018VerifiableCredentials;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.mvc.ri.se.PersistentStoragePaths;
import foundation.identity.jsonld.JsonLDObject;

public class Utils {

	/*
	 * public String vcWithClaims(String json) throws Exception {
	 * 
	 * RSA2018VerifiableCredentials rsaVerifiableCredentials = new
	 * RSA2018VerifiableCredentials(); Map<String, Object> claims =
	 * rsaVerifiableCredentials.getClaims(json); Object objEnc =
	 * claims.get(VC.ENCRYPTEDCLAIMS); if (!Objects.isNull(objEnc)) { throw new
	 * Exception("Claims are protected !"); }
	 * 
	 * return json; }
	 */

	public String vcWithClaims(String json, byte[] key) throws Exception {

		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> claims = rsaVerifiableCredentials.getClaims(json);
		String encryptedClaims = claims.get(VC.ENCRYPTEDCLAIMS).toString();
		CryptoProcessor cryptoProcessor = new CryptoProcessor();

		byte[] encClaims = cryptoProcessor.decrypt(key, ENCODING_DECODING_SCHEME.BASE64, SecurityContext.AES128,
				encryptedClaims.getBytes());

		String simpleVC = removeClaims(json);
		Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(new String(encClaims), Map.class);
		String vc = addClaims(simpleVC, claimsData);
		return vc;
	}

	public String vpWithClaims(String json, byte[] key) throws Exception {

		VerifiablePresentation vp = new VerifiablePresentation(json);
		Object obj = vp.getJsonObject().get(VC.PRESENTATION);
		if (Objects.isNull(obj)) {
			throw new Exception("Presentation attribute not found !");
		}

		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> presenation = (Map<String, Object>) obj;
		VerifiableCredential vc = new VerifiableCredential(new ObjectMapper().writeValueAsString(presenation));
		Map<String, ExtendedKeyValue> claims = vc.getClaims(key);

		ExtendedKeyValueList list = new ExtendedKeyValueList();
		Iterator iterator = claims.keySet().iterator();
		while (iterator.hasNext()) {
			String att = iterator.next().toString();
			list.put(att, claims.get(att));
		}

		String simpleVC = removeClaims(vc.toJson());

		String vc1 = addClaims(simpleVC, list);
		JsonLDObject jsonObj = JsonLDObject.fromJson(json);

		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		map.remove(VC.PRESENTATION);
		map.put(VC.PRESENTATION, jsonObj.fromJson(vc1).toMap());
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		
		return jsonObj.toJson(true);

	}

	public ExtendedKeyValueList extractSecureClaims(String json, byte[] key) throws Exception {
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> claims = rsaVerifiableCredentials.getClaims(json);
		Object obj = claims.get(VC.ENCRYPTEDCLAIMS);
		if (Objects.isNull(obj)) {
			throw new Exception("Could not find protected claims !");
		}
		CryptoProcessor cryptoProcessor = new CryptoProcessor();
		byte[] encClaims = cryptoProcessor.decrypt(key, ENCODING_DECODING_SCHEME.BASE64, SecurityContext.AES128,
				obj.toString().getBytes());
		ExtendedKeyValueList list = new ObjectMapper().readValue(new String(encClaims), ExtendedKeyValueList.class);
		return list;
	}

	public String getDidcomm(String json) throws Exception {
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		Map<String, Object> claims = rsaVerifiableCredentials.getClaims(json);
		String didcomR = claims.get(VC.DiDCOM).toString();
		return didcomR;
	}

	public String addSecureClaims(String json, Object claims, String didcom, byte[] key) throws Exception {

		CryptoProcessor cryptoProcessor = new CryptoProcessor();

		byte[] encClaims = cryptoProcessor.encrypt(key, ENCODING_DECODING_SCHEME.BASE64, SecurityContext.AES128,
				new ObjectMapper().writeValueAsString(claims).getBytes());

		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		SecureClaims secureClaims = new SecureClaims();
		secureClaims.setDidcom(didcom);
		secureClaims.setEncoding(ENCODING_DECODING_SCHEME.BASE64.value());
		secureClaims.setEncryptedClaims(new String(encClaims));
		secureClaims.setType(SecurityContext.AES128 + VC.ALGO);
		map.put(VC.CLAIMS, secureClaims);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		return jsonObj.toJson(true);
	}

	public String addClaims(String json, Object claims) throws Exception {
		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		map.put(VC.CLAIMS, claims);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		return jsonObj.toJson(true);
	}
	public String addMetaMapper(String json, Object metamapper) throws Exception {
		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		map.put(VC.METAMAPPER, metamapper);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		return jsonObj.toJson(true);
	}

	public boolean verify(String json) throws Exception {
		String clearJson = removeClaims(json);
		return verifyVC(clearJson);
	}

	private boolean verifyVC(String json) throws Exception {
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		// fetch ip address of server against serverEtherAddress
		if (!rsaVerifiableCredentials.verifyOffline(json)) {
			throw new Exception("Credentials verification failed !");
		}
		return true;
	}

	private String removeClaims(String json) throws Exception {
		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		map.remove(VC.CLAIMS);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		return jsonObj.toJson(true);
	}

	public RSAAsymmetricKeyPair getPrivateKey(String username, String password) throws Exception {
		DistinguishName distinguishName = DistinguishName.builder().name(username).build();
		StoreHandling storeHandling = new StoreHandling();
		CertificateSuite certificateSuite = new CertificateSuite(username, "storename",CertificateHandlingBC.getClientKeyUsage());
		return new RSAAsymmetricKeyPair(
				storeHandling.fetchCertificate(certificateSuite, distinguishName).getPublicKey(),
				storeHandling.fetchPrivateKey(certificateSuite, password, distinguishName));
	}
	
	public Credentials getCredentials(String walletDir, String username, String password) throws Exception {
		try {
			if (!walletDir.endsWith(File.separator)) {
				walletDir = walletDir + File.separator;
			}
			String path = "";
			String userDir = walletDir + username;
			if (Objects.isNull(new File(userDir)) && new File(userDir).list().length == 0) {
				System.err.println("Credentials do not exist !");
				throw new Exception("Credentials do not exist !");
			} else {
				System.err.println("Credentials already exisit in the wallet !");
				File fl[] = new File(userDir).listFiles();
				for (File f : fl) {
					if (f.getName().endsWith(".json")) {
						path = f.getName();
					}
				}
			}
			return WalletUtils.loadCredentials(password, userDir + "/" + path);
		} catch (Exception e) {
			System.err.println("===> Problems when extracting (creating) credentials !");
			System.err.println(e.getMessage());
			throw new Exception(e);
		}
	}
	public boolean checkExpiryDate(String endDateStr) throws VeidblockException {

		Date theExpiryDate = null;
		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			theExpiryDate = sdf.parse(endDateStr);
		} catch (Exception exp) {
			throw new VeidblockException("JWS Token expired - !");
		}

		Date currentDateTime = new Date();

		boolean before = currentDateTime.before(theExpiryDate);

		if (before) {
			return true;
		} else {
			//logger.error(marker + " Session expired !");
			throw new VeidblockException("Error validating access token: (Session has expired on " + endDateStr
					+ ". The current time is " + sdf.format(currentDateTime) + " )!");
		}
	}
}
