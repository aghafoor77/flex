package com.rise.vdr.api.persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "SCTransaction")
@XmlRootElement
public class SCTransaction{
	@Id
	@Column(name = "transactionID")
	private String transactionID;
	private long transactiondDate; // in mills 
	private String trasactionReceiver;
	private String tarnsactionOwner;
	private String animalID;
	public SCTransaction (){

	}
	public SCTransaction ( String transactionID,long transactiondDate,String trasactionReceiver,String tarnsactionOwner,String animalID){
		this.transactionID = transactionID; 
		this.transactiondDate = transactiondDate; 
		this.trasactionReceiver = trasactionReceiver; 
		this.tarnsactionOwner = tarnsactionOwner; 
		this.animalID = animalID; 
	}

	public String getTransactionID(){
		return this.transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID; 
	}
	public long getTransactiondDate(){
		return this.transactiondDate;
	}
	public void setTransactiondDate(long transactiondDate) {
		this.transactiondDate = transactiondDate; 
	}
	public String getTrasactionReceiver(){
		return this.trasactionReceiver;
	}
	public void setTrasactionReceiver(String trasactionReceiver) {
		this.trasactionReceiver = trasactionReceiver; 
	}
	public String getTarnsactionOwner(){
		return this.tarnsactionOwner;
	}
	public void setTarnsactionOwner(String tarnsactionOwner) {
		this.tarnsactionOwner = tarnsactionOwner; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
}
