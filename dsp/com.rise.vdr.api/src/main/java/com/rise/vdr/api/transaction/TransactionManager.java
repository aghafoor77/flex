package com.rise.vdr.api.transaction;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ri.se.ipfsj.v2.DocumentManager;
import org.ri.se.ipfsj.v2.IPFSFileDescriptor;
import org.ri.se.selectivedisclosure.RSAAsymmetricKeyPair;
import org.ri.se.selectivedisclosure.vc.VerifiableCredential;
import org.ri.se.selectivedisclosure.vc.VerifiableCredentialManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.crypto.tink.subtle.Random;
import com.rise.vdr.api.persistance.SCEvidence;
import com.rise.vdr.api.persistance.SCEvidenceService;
import com.rise.vdr.api.persistance.SCSecretKey;
import com.rise.vdr.api.persistance.SCSecretKeyService;
import com.rise.vdr.api.persistance.SCTransaction;
import com.rise.vdr.api.persistance.SCTransactionService;
import com.rise.vdr.api.utils.ALEvidenceObjectContainer;

import io.ipfs.api.IPFS;

public class TransactionManager {

	private SCTransactionService scTransactionService;
	private SCEvidenceService scevidenceService;
	private SCSecretKeyService scsecretkeyService;

	public TransactionManager(SCTransactionService scTransactionService, SCEvidenceService scevidenceService,
			SCSecretKeyService scsecretkeyService) {
		this.scTransactionService = scTransactionService;
		this.scevidenceService = scevidenceService;
		this.scsecretkeyService = scsecretkeyService;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SCEvidence evidence = new SCEvidence();
		evidence.setAccessLevel("PREVIOUS");
		evidence.setEid("eid-" + System.currentTimeMillis());
		evidence.setResType("Evidence");
		evidence.setTransactionID("tansID");
		Map<String, EvidenceContainer> map = new HashMap<String, EvidenceContainer>();

		EvidenceContainer ec = new EvidenceContainer();
		EvidenceObject evidenceObject = new EvidenceObject();
		evidenceObject.setEvidence(evidence);
		evidenceObject.setEvidenceType("SIMPLE");
		ec.add(evidenceObject);
		map.put("AR-12323223", ec);
		// new TransactinManager().manage("/ip4/127.0.0.1/tcp/5001", "holder",
		// "controller", map);
	}

	public String uploadOnIPFS(String ipfsIP, String holder, String controller, Object evd) throws Exception {

		// ==================================================
		// Claims of the verifiable credentials
		// String holder = "0x71aD1108403C28f3723d09D533337BC115528039";
		// String controller = "0xC5B09bb75A2C4b6Bb0c91E9dac4d3cC3C40Fed05";

		controller = "" + controller;
		holder = "" + holder;

		// Generate a RSA Asymmetric key for VC
		VerifiableCredentialManager credentialManager = new VerifiableCredentialManager();
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		KeyPair pair = keyGen.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey vcCreatorPublicKey = pair.getPublic();
		RSAAsymmetricKeyPair keyPair = new RSAAsymmetricKeyPair(vcCreatorPublicKey, privateKey);

		// Creating VC
		Date issuedDate = new Date();
		Date validFrom = new Date();
		Date issued = new Date();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dateTimeInFiveYears = now.plusYears(15);
		Date expirationdate = Date.from(dateTimeInFiveYears.atZone(ZoneId.systemDefault()).toInstant());

		String objJson = new ObjectMapper().writeValueAsString(evd);
		VerifiableCredential jsonVC = credentialManager.create(controller, keyPair, objJson, holder,
				"did:veid:" + new Random().randInt(), "did:veid:" + new Random().randInt() + "com", "key2", issuedDate,
				validFrom, issued, expirationdate);
		String fielName = "" + new Random().randInt();
		File f = new File("" + fielName);
		Files.write(jsonVC.toJson().getBytes(), f);
		return uploadIPFS(ipfsIP, f.getAbsolutePath());

	}
	public byte[] generateRandomBytes() {
        byte[] randomBytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }
	public String uploadIPFS(String ipfsIP, String file) {
		IPFS ipfs = new IPFS(ipfsIP);
		DocumentManager documentManager = new DocumentManager(ipfs);
		IPFSFileDescriptor descriptor = documentManager.upload(file);
		System.out.println(descriptor.getHashBase58());
		if (new File(file).exists())
			new File(file).delete();
		return descriptor.getHashBase58();
	}

	public String getEncryptedSharedKey(byte key[]) {
		return new String(key);
	}

	public String manage(String ipfsIP, String to, String from, Map<String, EvidenceContainer> animalEvidence) {
		// Submit Transaction
		animalEvidence.forEach((key, value) -> {
			SCTransaction scTransaction = new SCTransaction();
			scTransaction.setTransactionID("T" + System.currentTimeMillis());
			scTransaction.setTarnsactionOwner(from);
			scTransaction.setTransactiondDate(System.currentTimeMillis());
			scTransaction.setTrasactionReceiver(to);
			scTransaction.setAnimalID(key);
			this.scTransactionService.post(scTransaction);

			for (EvidenceObject eo : value) {
				SCEvidence evidence = new SCEvidence();
				evidence.setAccessLevel("");
				evidence.setEid("eid-" + System.currentTimeMillis());
				evidence.setResType(eo.getEvidenceType());
				evidence.setTransactionID(scTransaction.getTransactionID());

				try {
					String ipfsLink = uploadOnIPFS(ipfsIP, to, from, eo.getEvidence());
					evidence.setLink(ipfsLink);
					scevidenceService.post(evidence);

					// Store
					SCSecretKey scSecretKey = new SCSecretKey();
					scSecretKey.setDid(to);
					scSecretKey.setEid(evidence.getEid());
					scSecretKey.setSharedkey(key);
					scSecretKey.setSsid("SK-" + System.currentTimeMillis());
					scsecretkeyService.post(scSecretKey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// Prepare Evidences
		// Upload Evidences
		// Store Evidences
		// Store Keys

		return "";
	}

	public String manageWithAccess(String ipfsIP, String to, String from, String slaughterhouse,
			Map<String, ALEvidenceObjectContainer> animalEvidence) {
		// Submit Transaction
		animalEvidence.forEach((key, value) -> {
			SCTransaction scTransaction = new SCTransaction();
			scTransaction.setTransactionID("T" + System.currentTimeMillis());
			scTransaction.setTarnsactionOwner(from);
			scTransaction.setTransactiondDate(System.currentTimeMillis());
			scTransaction.setTrasactionReceiver(to);
			scTransaction.setAnimalID(key);
			this.scTransactionService.post(scTransaction);
			Random r = new Random();
			//AL EvidenceObject in the values
			value.forEach((eo) -> {

				
					SCEvidence evidence = new SCEvidence();
					evidence.setAccessLevel(eo.getAccessLevel());
					evidence.setEid("eid-" + System.currentTimeMillis() + ":" + r.randInt());
					evidence.setResType(eo.getEvidence().getEvidenceType());
					evidence.setTransactionID(scTransaction.getTransactionID());
					try {
						String ipfsLink = uploadOnIPFS(ipfsIP, to, from, eo.getEvidence());
						evidence.setLink(ipfsLink);
						scevidenceService.post(evidence);
						String did="";
						if (eo.getAccessLevel().equalsIgnoreCase("Slaughterhouse")) {

							did = slaughterhouse;

						} else if (eo.getAccessLevel().equalsIgnoreCase("Agencies")) {
							did = "";

						} else if (eo.getAccessLevel().equalsIgnoreCase("Transporter")) {
							did = to;

						} else if (eo.getAccessLevel().equalsIgnoreCase("ALL")) {
							did = "Open";

						} else if (eo.getAccessLevel().equalsIgnoreCase("Both")) {
							addSCSecret(to, evidence.getEid(), key);
							addSCSecret(slaughterhouse, evidence.getEid(), key);
							return;

						}
						addSCSecret(did, evidence.getEid(), key);
						
						// Store

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				
			});
		});

		// Prepare Evidences
		// Upload Evidences
		// Store Evidences
		// Store Keys

		return "";
	}
	
private boolean addSCSecret(String did, String eid, String key) {
	SCSecretKey scSecretKeySH = new SCSecretKey();
	scSecretKeySH.setDid(did);
	scSecretKeySH.setEid(eid);
	scSecretKeySH.setSharedkey(key);
	scSecretKeySH.setSsid("SK-" + System.currentTimeMillis() + ":" + Random.randInt());
	scsecretkeyService.post(scSecretKeySH);
	return true;
}

	// SCResourceService scresourceService;

}
