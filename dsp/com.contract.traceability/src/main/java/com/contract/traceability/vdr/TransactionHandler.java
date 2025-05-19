package com.contract.traceability.vdr;

import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {

	public static void main(String[] args) {

		Transaction t = new Transaction();
		t.setAnimalID("animalid");
		t.setTarnsactionOwner("ownerdid");
		t.setTrasactionReceiver("ownerrec");
		List<Evidence> evidences = new ArrayList<Evidence>();
		t.setEvidences(evidences);
	}

	public List<Evidence> prepareEvidences(String animalID) {
		// Fetch all data from DB against animal
		List<Evidence> evidences = new ArrayList<Evidence>();

		return null;
	}

	public boolean submit(Transaction t) {

		return true;
	}
}
