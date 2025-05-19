package org.ri.se.selectivedisclosure.vc;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

import org.ri.se.selectivedisclosure.ExtendedKeyValueList;
import org.ri.se.selectivedisclosure.RSAAsymmetricKeyPair;
import org.ri.se.selectivedisclosure.utils.ClaimsAttributedVerifier;
import org.ri.se.selectivedisclosure.utils.Utils;
import org.ri.se.selectivedisclosure.vp.VCExtendedPresentationEngine;
import org.ri.se.selectivedisclosure.vp.VerifiablePresentation;
import org.ri.se.verifiablecredentials.asymmetric.RSA2018VerifiableCredentials;
import org.ri.se.verifiablecredentials.entities.ProofAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import foundation.identity.jsonld.JsonLDObject;

public class VerifiableCredentialManager {

	public VerifiableCredential create(String controller, RSAAsymmetricKeyPair asymmetricPair, String subjectClaims,
			String holder, String did, String didcom, String keyNumber, byte[] key, Date issuedDate, Date validFrom,
			Date issued, Date expirationdate) throws Exception {
		ExtendedKeyValueList list = new ExtendedKeyValueList();

		Map<String, Object> claimsData = (Map<String, Object>) new ObjectMapper().readValue(subjectClaims, Map.class);
		// JSONObject inJSON = new JSONObject(claimsData);
		VerifiableCredential vcJson = createVerifiableCredential(controller, asymmetricPair, claimsData, holder, did,
				didcom, keyNumber, issuedDate, validFrom, issued, expirationdate);
		VerifiableCredential vc = new VerifiableCredential(
				new Utils().addSecureClaims(vcJson.toJson(), vcJson.getClaims(), didcom, key));
		return vc;
	}

	public VerifiableCredential create(String controller, RSAAsymmetricKeyPair asymmetricPair, String subjectClaims,
			String holder, String did, String didCom, String keyNumber, Date issuedDate, Date validFrom, Date issued,
			Date expirationdate) throws Exception {
		Map<String, Object> claimsData1 = (Map<String, Object>) new ObjectMapper().readValue(subjectClaims, Map.class);
		// JSONObject inJSON = new JSONObject(claimsData1);
		VerifiableCredential vc = createVerifiableCredential(controller, asymmetricPair, claimsData1, holder, did,
				didCom, keyNumber, issuedDate, validFrom, issued, expirationdate);
		return vc;
	}

	private VerifiableCredential createVerifiableCredential(String controller, RSAAsymmetricKeyPair asymmetricPair,
			Map<String, Object> claimsData, String holder, String did, String didcom, String keyNumber, Date issuedDate,
			Date validFrom, Date issued, Date expirationdate) throws Exception {
		// claimsData
		// Add encrypted symmetric key with key agreement tag
		// Creating credentials
		HashMap<String, Object> baseJson = new HashMap<String, Object>();
		baseJson.put(VC.DID, did);
		baseJson.put(VC.ISSUER, controller);
		baseJson.put(VC.HOLDER, holder);
		if (!Objects.isNull(issuedDate)) {
			baseJson.put(VC.ISSUEDDATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(issuedDate));
		} else {
			baseJson.put(VC.ISSUEDDATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}
		if (!Objects.isNull(validFrom)) {
			baseJson.put(VC.VALIDFROM, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(validFrom));
		} else {
			baseJson.put(VC.VALIDFROM, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}
		if (!Objects.isNull(issued)) {
			baseJson.put(VC.ISSUED, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(issued));
		} else {
			baseJson.put(VC.ISSUED, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}
		if (!Objects.isNull(expirationdate)) {
			baseJson.put(VC.EXPIRATIONDATE, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		}
		Vector<String> vec = new Vector();
		vec.add(VC.VERIFIABLECREDENTIAL);
		baseJson.put(VC.TYPE, vec);
		Map<String, Object> properties = new HashMap<String, Object>();
		VCExtendedEngine vcExtendedEngine = new VCExtendedEngine(claimsData);
		Map<String, String> digestList = vcExtendedEngine.getDigestSet();
		properties.put(VC.DIGEST_SET, digestList);

		ProofAttributes proofAtt = new ProofAttributes();
		proofAtt.setDomain(VC.DOMAIN);
		proofAtt.setVerificationMethod(VC.VERIFICATIONMETHOD);
		proofAtt.setPurpose("VERIFICATIONMETHOD");

		// = new Utils().getPrivateKey(username, password);
		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		URI uriController = Objects.isNull(did) ? null : new URI(did);
		String json = rsaVerifiableCredentials.create(uriController, keyNumber, baseJson, properties,
				asymmetricPair.getPrivateKey().getEncoded(), asymmetricPair.getPublicKey().getEncoded(), proofAtt);
		Map<String, Object> claimsExtendedData = (Map<String, Object>) new ObjectMapper()
				.readValue(vcExtendedEngine.getExtendedJSON(), Map.class);
		VerifiableCredential vc = new VerifiableCredential(new Utils().addClaims(json, claimsExtendedData));
		vc = new VerifiableCredential(new Utils().addMetaMapper(vc.toJson(), vcExtendedEngine.getMetaMapper()));

		return vc;
	}

	private String createVerifiablePresentation(String holder, RSAAsymmetricKeyPair asymmetricPair,
			JsonLDObject verifiablePresentatin, String presenter) throws Exception {
		// claimsData
		// Add encrypted symmetric key with key agreement tag
		// Creating credentials

		HashMap<String, Object> baseJson = new HashMap<String, Object>();
		baseJson.put(VC.VERIFIER, presenter);
		baseJson.put(VC.HOLDER, holder);
		Vector<String> vec = new Vector();
		vec.add(VC.VERIFIABLEPRESENTATION);
		baseJson.put(VC.TYPE, vec);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(VC.PRESENTATION, verifiablePresentatin);

		ProofAttributes proofAtt = new ProofAttributes();
		proofAtt.setDomain(VC.DOMAIN);
		proofAtt.setVerificationMethod(VC.VERIFICATIONMETHOD);
		proofAtt.setPurpose("VERIFICATIONMETHOD");

		RSA2018VerifiableCredentials rsaVerifiableCredentials = new RSA2018VerifiableCredentials();
		String json = rsaVerifiableCredentials.create(null, holder + "#key1", baseJson, properties,
				asymmetricPair.getPrivateKey().getEncoded(), asymmetricPair.getPublicKey().getEncoded(), proofAtt);

		return json;
	}

	public VerifiablePresentation createVerifiablePresentation(String json, String presentedto,
			ArrayList<String> requestedItems, RSAAsymmetricKeyPair asymmetricPair, String didcom, byte[] key)
			throws Exception {

		VerifiableCredential vc = new VerifiableCredential(json);
		vc.verifyOffline();

		Map<String, Object> claims = vc.getClaims();

		VCExtendedPresentationEngine vcExtendedEngine = new VCExtendedPresentationEngine(claims, requestedItems);
		String jsonExt = vcExtendedEngine.getExtendedJSON();
		Map<String, Object> presentData = (Map<String, Object>) new ObjectMapper().readValue(jsonExt, Map.class);

		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		map.remove(VC.CLAIMS);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		String jsonUpdated = new Utils().addSecureClaims(vcJson, presentData, didcom, key);
		VerifiableCredential credential = new VerifiableCredential(jsonUpdated);
		String holder = map.get(VC.HOLDER).toString();
		// System.out.println(credential.get());
		String vcUp = createVerifiablePresentation(holder, asymmetricPair, credential.get(), presentedto);
		return new VerifiablePresentation(vcUp);
	}

	public VerifiablePresentation createVerifiablePresentation(String json, String presentedto,
			List<String> requestedItems, RSAAsymmetricKeyPair asymmetricPair) throws Exception {

		VerifiableCredential vc = new VerifiableCredential(json);
		vc.verifyOffline();

		Map<String, Object> claims = vc.getClaims();
		//
		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(json);
		Map<String, Object> map = jsonObj.toMap();
		// Get metamapper object and translate requested items
		Map<String, String> metaMapper = (Map<String, String>) map.get(VC.METAMAPPER);
		List<String> translatedRequestedItems = new ArrayList<String>();
		for (String item : requestedItems) {
			if (metaMapper.containsKey(item)) {
				translatedRequestedItems.add(metaMapper.get(item));
			} else {
				throw new Exception("Requested item '" + item + "' does not exisit in the document !");
			}
		}
		VCExtendedPresentationEngine vcExtendedEngine = new VCExtendedPresentationEngine(claims,
				translatedRequestedItems);
		String jsonExt = vcExtendedEngine.getExtendedJSON();
		Map<String, Object> presentData = (Map<String, Object>) new ObjectMapper().readValue(jsonExt, Map.class);
		map.remove(VC.CLAIMS);
		map.remove(VC.METAMAPPER);
		String vcJson = new ObjectMapper().writeValueAsString(map);
		jsonObj = jsonObj.fromJson(vcJson);
		String jsonUpdated = new Utils().addClaims(jsonObj.toJson(true), presentData);
		VerifiableCredential credential = new VerifiableCredential(jsonUpdated);
		String holder = map.get(VC.HOLDER).toString();
		String vcUp = createVerifiablePresentation(holder, asymmetricPair, credential.get(), presentedto);
		return new VerifiablePresentation(vcUp);
	}

	public boolean verifyClaimsAttributes(Map<String, Object> claims, Map<String, Object> digest) throws Exception {

		attributesVerification(claims, digest);
		return true;
	}

	//
	public boolean attributesVerification(Map<String, Object> claimsData, Map<String, Object> digest) throws Exception {
		ClaimsAttributedVerifier claimsAttributedVerifier = new ClaimsAttributedVerifier();
		return claimsAttributedVerifier.verifyAttribute(claimsData, digest);
	}

	public String removeClaims(String vc) throws IOException {
		JsonLDObject jsonObj = new JsonLDObject();
		jsonObj = jsonObj.fromJson(vc);
		Map<String, Object> map = jsonObj.toMap();
		map.remove(VC.CLAIMS);
		map.remove(VC.METAMAPPER);
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
	}
}