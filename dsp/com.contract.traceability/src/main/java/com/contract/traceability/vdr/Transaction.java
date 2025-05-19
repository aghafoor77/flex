package com.contract.traceability.vdr;

import java.util.List;

public class Transaction {
	private String transactionID;;
	private String animalID;
	private String tarnsactionOwner; // DID
	private String trasactionReceiver;
	private List<Evidence> evidences;
	private String transactiondDate;
	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getAnimalID() {
		return animalID;
	}

	public void setAnimalID(String animalID) {
		this.animalID = animalID;
	}

	public String getTarnsactionOwner() {
		return tarnsactionOwner;
	}

	public void setTarnsactionOwner(String tarnsactionOwner) {
		this.tarnsactionOwner = tarnsactionOwner;
	}

	public String getTrasactionReceiver() {
		return trasactionReceiver;
	}

	public void setTrasactionReceiver(String trasactionReceiver) {
		this.trasactionReceiver = trasactionReceiver;
	}

	public List<Evidence> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<Evidence> evidences) {
		this.evidences = evidences;
	}

	public String getTransactiondDate() {
		return transactiondDate;
	}

	public void setTransactiondDate(String transactiondDate) {
		this.transactiondDate = transactiondDate;
	}
	
}
