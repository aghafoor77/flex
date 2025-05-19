package com.ri.se.aggregation.dymmydata;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.ri.se.ipfsj.v2.DocumentManager;
import org.ri.se.ipfsj.v2.IPFSFileDescriptor;
import org.ri.se.selectivedisclosure.RSAAsymmetricKeyPair;
import org.ri.se.selectivedisclosure.vc.VC;
import org.ri.se.selectivedisclosure.vc.VerifiableCredential;
import org.ri.se.selectivedisclosure.vc.VerifiableCredentialManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.crypto.tink.subtle.Random;

import io.ipfs.api.IPFS;

public class LSCResults {

	public static void main(String[] args) throws Exception {
		// System.out.println(result.get(0));
		LogManager.getLogManager().reset();
		Logger.getLogger("").setLevel(java.util.logging.Level.OFF);
		vcCreation();
		vcCreationAnUploading();
	}

	public static void vcCreation() throws Exception {

		// Disable Log4j (if used)
		System.setProperty("log4j2.level", "OFF");
		System.setProperty("org.apache.logging.log4j.simplelog.level", "OFF");

		String obj = FarmDummyData.fetchData();
		// Claims of the verifiable credentials
		String holder = "0x71aD1108403C28f3723d09D533337BC115528039";
		String controller = "0xC5B09bb75A2C4b6Bb0c91E9dac4d3cC3C40Fed05";

		controller = VC.PREID + controller;
		holder = VC.PREID + holder;

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
		Date expirationdate = new Date();
		AnimalWithEvidence animalWithEvidence = new AnimalWithEvidence();
		String employeeID = "";
		String animalIDMother = "";
		String breed = "";
		String motherExaminationId = "";
		animalWithEvidence
				.setDtoRegisterAnimal1(FarmDummyData.getAnimal(employeeID, animalIDMother, breed, motherExaminationId));
		List<Object> anAnimalRecord = FarmDummyData
				.anAnimalReocrd(animalWithEvidence.getDtoRegisterAnimal1().getAnimalID());
		List<String> result = new ArrayList<String>();// DummyData.anAnimalReocrd();
		long startTime;
		long endTime;
		Random random = new Random();
		int numberOfEvidences = 21;
		Scanner scanner = new Scanner(System.in); // Create Scanner instance
		System.out.println("Press enter run VC creation results !");
		String input = scanner.nextLine(); // Waits for the user to press Enter
		scanner.close(); // Close scanner to free resources
		System.out.println("Number of Evidence \t time to process!");
		for (int j = 0; j < numberOfEvidences; j++) {
			startTime = System.currentTimeMillis();
			for (int i = 0; i < j; i++) {
				Object aRecord = anAnimalRecord.get(random.randInt(anAnimalRecord.size() - 1));
				animalWithEvidence.getEvidence().add(aRecord);
			}
			String objJson = new ObjectMapper().writeValueAsString(animalWithEvidence);
			VerifiableCredential jsonVC = credentialManager.create(controller, keyPair, objJson, holder,
					"did:veid:" + random.randInt(10000000), "did:veid:" + new Random().randInt() + "com", "key2",
					issuedDate, validFrom, issued, expirationdate);
			endTime = System.currentTimeMillis();
			System.out.println(j + "\t\t\t" + (endTime - startTime));
		}
	}
	public static void vcCreationAnUploading() throws Exception {

		// Disable Log4j (if used)
		System.setProperty("log4j2.level", "OFF");
		System.setProperty("org.apache.logging.log4j.simplelog.level", "OFF");

		String obj = FarmDummyData.fetchData();
		// Claims of the verifiable credentials
		String holder = "0x71aD1108403C28f3723d09D533337BC115528039";
		String controller = "0xC5B09bb75A2C4b6Bb0c91E9dac4d3cC3C40Fed05";

		controller = VC.PREID + controller;
		holder = VC.PREID + holder;

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
		Date expirationdate = new Date();
		AnimalWithEvidence animalWithEvidence = new AnimalWithEvidence();
		String employeeID = "";
		String animalIDMother = "";
		String breed = "";
		String motherExaminationId = "";
		animalWithEvidence
				.setDtoRegisterAnimal1(FarmDummyData.getAnimal(employeeID, animalIDMother, breed, motherExaminationId));
		List<Object> anAnimalRecord = FarmDummyData
				.anAnimalReocrd(animalWithEvidence.getDtoRegisterAnimal1().getAnimalID());
		List<String> result = new ArrayList<String>();// DummyData.anAnimalReocrd();
		long startTime;
		long endTime;
		Random random = new Random();
		int numberOfEvidences = 21;
		System.out.println("Number of Evidence \t time to process!");
		for (int j = 0; j < numberOfEvidences; j++) {
			startTime = System.currentTimeMillis();
			for (int i = 0; i < j; i++) {
				Object aRecord = anAnimalRecord.get(random.randInt(anAnimalRecord.size() - 1));
				animalWithEvidence.getEvidence().add(aRecord);
			}
			long didRnd = random.randInt(10000000);
			String objJson = new ObjectMapper().writeValueAsString(animalWithEvidence);
			VerifiableCredential jsonVC = credentialManager.create(controller, keyPair, objJson, holder,
					"did:veid:" + didRnd , "did:veid:" + new Random().randInt() + "com", "key2",
					issuedDate, validFrom, issued, expirationdate);
			String fielName= ""+didRnd;
			File f = new File("/home/chaincode/Desktop/dsp/formas-main/farm/others/com.ri.se.aggregation/output/"+fielName);
			Files.write(jsonVC.toJson().getBytes(), f);
			uploadIPFS(j, f.getAbsolutePath(), startTime);			
		}
	}
	public static void uploadIPFS(int j, String file, long startTime) {
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		IPFSFileDescriptor descriptor = documentManager.upload(file);//"/home/chaincode/Desktop/dsp/formas-main/dsp/org.ri.se.ipfsj/ab.txt");
		//System.out.println(descriptor.getHashBase58());
		//System.out.println(new String(documentManager.download(descriptor.getHashBase58())));
		long endTime = System.currentTimeMillis();
		System.out.println(j + "\t\t\t" + (endTime - startTime));
		//System.out.println(documentManager.download(descriptor.getHashBase58(), new FileOutputStream("down.txt")));
	}

}
