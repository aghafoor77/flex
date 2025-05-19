package com.ri.se.semenutilization.dto;

import java.util.Date;

public class DTOSemenUtilization{

	private String suid;
	private String insemationDate;
	private String notes;
	private String employeeID;
	private String osid;
	private String animalID;
	public DTOSemenUtilization (){

	}
	public DTOSemenUtilization ( String suid,String insemationDate,String notes,String employeeID,String osid,String animalID){
		this.suid = suid; 
		this.insemationDate = insemationDate; 
		this.notes = notes; 
		this.employeeID = employeeID; 
		this.osid = osid; 
		this.animalID = animalID; 
	}

	public String getSuid(){
		return this.suid;
	}
	public void setSuid(String suid) {
		this.suid = suid; 
	}
	public String getInsemationDate(){
		return this.insemationDate;
	}
	public void setInsemationDate(String insemationDate) {
		this.insemationDate = insemationDate; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getOsid(){
		return this.osid;
	}
	public void setOsid(String osid) {
		this.osid = osid; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
}
