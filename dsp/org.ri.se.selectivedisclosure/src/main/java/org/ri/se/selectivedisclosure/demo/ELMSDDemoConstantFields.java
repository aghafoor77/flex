

package org.ri.se.selectivedisclosure.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.ri.se.selectivedisclosure.RSAAsymmetricKeyPair;
import org.ri.se.selectivedisclosure.vc.VC;
import org.ri.se.selectivedisclosure.vc.VerifiableCredential;
import org.ri.se.selectivedisclosure.vc.VerifiableCredentialManager;
import org.ri.se.selectivedisclosure.vp.VerifiablePresentation;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ELMSDDemoConstantFields {

	public static void main(String[] args) throws Exception {
		new ELMSDDemoConstantFields().createMicrocredentialVC();
	}

	public void pressEnter() {
		System.err.println("Press Enter ");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String createMicrocredentialVC() throws Exception {

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
		byte[] dataIn = Files.readAllBytes(
				Paths.get("/home/gpvm1/Desktop/RISE/projects/mvc/microcredentials/backend/org.ri.se.selectivedisclosure/elm.json"));
		System.err.println("============= INPUT ================");
		System.out.println(new String(dataIn));
		pressEnter();
		// Creating VC
		Date issuedDate = new Date();
		Date validFrom = new Date();
		Date issued = new Date();
		Date expirationdate = new Date();
		VerifiableCredential jsonVC = credentialManager.create(controller, keyPair, new String(dataIn), holder, "did:veid:docdid", "did:veid:23232","key2", issuedDate, validFrom, issued, expirationdate);
		String json = jsonVC.toJson();
		System.err.println("============= VC with Claims ================");
		System.out.println(json);
		pressEnter();
		System.err.println("============= Offline verification ================");
		System.out.println(jsonVC.verifyOffline());
		pressEnter();
		System.err.println("============= Claims Attribute Verification ================");
		System.out.println(jsonVC.verifyClaimsAttributes());
		pressEnter();
		System.err.println("============= Claims ================");
		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonVC.getClaims()));
		pressEnter();
		System.err.println("============= VC without claims ================");
		String vcwoclaims = credentialManager.removeClaims(json);
		System.out.println(vcwoclaims);
		pressEnter();
		System.err.println("============= offlinbe verification VC without claims ================");
		VerifiableCredential jsonVCWOClaims = new VerifiableCredential(vcwoclaims);
		System.out.println(jsonVCWOClaims.verifyOffline());
		pressEnter();
		System.err.println("============= Creating presentation ================");
		ArrayList<String> toInclude = new ArrayList<String>();
		toInclude.add("elm:EuropeanDigitalCredential.cred:issued"); //2.10
		toInclude.add("elm:EuropeanDigitalCredential.cred:issuer.elm:eidasLegalIdentifier.dc:type"); // 2.8
		toInclude.add("elm:EuropeanDigitalCredential.cred:credentialSubject.elm:hasClaim.elm:LearningAchievement.elm:learningOpportunity.dc:title"); //2.4.2.3.1.8.1
		VerifiablePresentation vp = credentialManager.createVerifiablePresentation(json, "did:veid:"+controller, toInclude, keyPair);
		System.out.println(vp.toJson());
		System.out.println(vp.verifyOffline());
		System.out.println(vp.verifyClaimsAttributes());
		
		// toInclude.add("1");
		// toInclude.add("2");
		// toInclude.add("3.2");
		// VCExtendedPresentationEngine vcExtendedEngine = new
		// VCExtendedPresentationEngine(claimsData, toInclude);
		// String jsonExt = vcExtendedEngine.getExtendedJSON();

		return json;
	}

}
