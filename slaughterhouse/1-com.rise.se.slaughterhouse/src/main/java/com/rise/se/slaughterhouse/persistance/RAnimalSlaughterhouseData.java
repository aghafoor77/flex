package com.rise.se.slaughterhouse.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "RAnimalSlaughterhouseData")

public class RAnimalSlaughterhouseData{
	@Id
	@Column(name = "animalID")
	private String animalID;
	private String animalIDMother;
	private int currentWeight;
	private String currentStatus;
	private String sex;
	private String farmId;
	private String transferDate;
	private String birthDate;
	private String transactionID;
	private String breed;
	private String birthPlace;
	private String previousFarmContact;
	private String receivedFarmName;
	public RAnimalSlaughterhouseData (){

	}
	public RAnimalSlaughterhouseData ( String animalID,String animalIDMother,int currentWeight,String currentStatus,String sex,String farmId,String transferDate,String birthDate,String transactionID,String breed,String birthPlace,String previousFarmContact,String receivedFarmName){
		this.animalID = animalID; 
		this.animalIDMother = animalIDMother; 
		this.currentWeight = currentWeight; 
		this.currentStatus = currentStatus; 
		this.sex = sex; 
		this.farmId = farmId; 
		this.transferDate = transferDate; 
		this.birthDate = birthDate; 
		this.transactionID = transactionID; 
		this.breed = breed; 
		this.birthPlace = birthPlace; 
		this.previousFarmContact = previousFarmContact; 
		this.receivedFarmName = receivedFarmName; 
	}

	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getAnimalIDMother(){
		return this.animalIDMother;
	}
	public void setAnimalIDMother(String animalIDMother) {
		this.animalIDMother = animalIDMother; 
	}
	public int getCurrentWeight(){
		return this.currentWeight;
	}
	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight; 
	}
	public String getCurrentStatus(){
		return this.currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus; 
	}
	public String getSex(){
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex; 
	}
	public String getFarmId(){
		return this.farmId;
	}
	public void setFarmId(String farmId) {
		this.farmId = farmId; 
	}
	public String getTransferDate(){
		return this.transferDate;
	}
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate; 
	}
	public String getBirthDate(){
		return this.birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate; 
	}
	public String getTransactionID(){
		return this.transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID; 
	}
	public String getBreed(){
		return this.breed;
	}
	public void setBreed(String breed) {
		this.breed = breed; 
	}
	public String getBirthPlace(){
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace; 
	}
	public String getPreviousFarmContact(){
		return this.previousFarmContact;
	}
	public void setPreviousFarmContact(String previousFarmContact) {
		this.previousFarmContact = previousFarmContact; 
	}
	public String getReceivedFarmName(){
		return this.receivedFarmName;
	}
	public void setReceivedFarmName(String receivedFarmName) {
		this.receivedFarmName = receivedFarmName; 
	}
}
