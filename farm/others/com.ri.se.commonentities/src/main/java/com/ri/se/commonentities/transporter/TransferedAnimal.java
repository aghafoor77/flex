package com.ri.se.commonentities.transporter;

import java.util.Date;

public class TransferedAnimal {
	private String animalID;
	private String animalIDMother;
	private String birthPlace;
	private String previousFarmContact;
	private int currentWeight;
	private String receivedFarmName;
	private String sex;
	private String farmId;
	private Date transferDate;
	private Date birthDate;
	private String transactionID;
	private long transactionTime;
	private String breed;
	private String currentStatus;
	
	public TransferedAnimal() {

	}

	public TransferedAnimal ( String animalID,String animalIDMother,String birthPlace,String previousFarmContact,int currentWeight,String receivedFarmName,String sex,String farmId,Date transferDate,Date birthDate,String breed, String transactionID,long transactionTime, String currentStatus){
		this.animalID = animalID; 
		this.animalIDMother = animalIDMother; 
		this.birthPlace = birthPlace; 
		this.previousFarmContact = previousFarmContact; 
		this.currentWeight = currentWeight; 
		this.receivedFarmName = receivedFarmName; 
		this.sex = sex; 
		this.farmId = farmId; 
		this.transferDate = transferDate; 
		this.birthDate = birthDate; 
		this.breed = breed; 
		this.transactionID =transactionID;
		this.transactionTime= transactionTime;
		this.currentStatus = currentStatus;
	}

	public String getAnimalID() {
		return this.animalID;
	}

	public void setAnimalID(String animalID) {
		this.animalID = animalID;
	}

	public String getAnimalIDMother() {
		return this.animalIDMother;
	}

	public void setAnimalIDMother(String animalIDMother) {
		this.animalIDMother = animalIDMother;
	}

	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getPreviousFarmContact() {
		return this.previousFarmContact;
	}

	public void setPreviousFarmContact(String previousFarmContact) {
		this.previousFarmContact = previousFarmContact;
	}

	public int getCurrentWeight() {
		return this.currentWeight;
	}

	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}

	public String getReceivedFarmName() {
		return this.receivedFarmName;
	}

	public void setReceivedFarmName(String receivedFarmName) {
		this.receivedFarmName = receivedFarmName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFarmId() {
		return this.farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	public Date getTransferDate() {
		return this.transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBreed() {
		return this.breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(long transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}	
}
