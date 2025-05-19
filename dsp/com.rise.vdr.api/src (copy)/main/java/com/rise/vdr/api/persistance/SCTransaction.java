package com.rise.vdr.api.persistance;

import java.util.Date;
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
	@Column(name = "eid")
	private String eid;
	private String transactiondDate;
	private String trasactionReceiver;
	private String tarnsactionOwner;
	private String animalID;
	private String transactionID;
	public SCTransaction (){

	}
	public SCTransaction (String eid,String transactiondDate,String trasactionReceiver,String tarnsactionOwner,String animalID,String transactionID){
		this.eid = eid; 
		this.transactiondDate = transactiondDate; 
		this.trasactionReceiver = trasactionReceiver; 
		this.tarnsactionOwner = tarnsactionOwner; 
		this.animalID = animalID; 
		this.transactionID = transactionID; 
	}

	public String getEid(){
		return this.eid;
	}
	public void setEid(String eid) {
		this.eid = eid; 
	}
	public String getTransactiondDate(){
		return this.transactiondDate;
	}
	public void setTransactiondDate(String transactiondDate) {
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
	public String getTransactionID(){
		return this.transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID; 
	}
}
