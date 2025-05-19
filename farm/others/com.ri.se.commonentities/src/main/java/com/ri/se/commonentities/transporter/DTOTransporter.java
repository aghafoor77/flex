package com.ri.se.commonentities.transporter;

import java.util.Date;

public class DTOTransporter{

	private String TID;
	private String notes;
	private String animalPeryear;
	private String ownerId;
	private boolean isOrganization;
	public DTOTransporter (){

	}
	public DTOTransporter ( String TID,String notes,String animalPeryear,String ownerId,boolean isOrganization){
		this.TID = TID; 
		this.notes = notes; 
		this.animalPeryear = animalPeryear; 
		this.ownerId = ownerId; 
		this.isOrganization = isOrganization; 
	}

	public String getTID(){
		return this.TID;
	}
	public void setTID(String TID) {
		this.TID = TID; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getAnimalPeryear(){
		return this.animalPeryear;
	}
	public void setAnimalPeryear(String animalPeryear) {
		this.animalPeryear = animalPeryear; 
	}
	public String getOwnerId(){
		return this.ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId; 
	}
	public boolean getIsOrganization(){
		return this.isOrganization;
	}
	public void setIsOrganization(boolean isOrganization) {
		this.isOrganization = isOrganization; 
	}
}
