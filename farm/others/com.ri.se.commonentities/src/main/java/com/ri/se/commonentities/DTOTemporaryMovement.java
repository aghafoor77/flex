package com.ri.se.commonentities;

import java.util.Date;



public class DTOTemporaryMovement{

	private String tmid;
	private String outDate;
	private String notes;
	private String purpose;
	private String inDate;
	private String tmType;
	private String employeeID;
	private StringList animals;
	public DTOTemporaryMovement (){

	}
	public DTOTemporaryMovement ( String tmid,String outDate,String notes,String purpose,String inDate,String tmType,String employeeID, StringList animals){
		this.tmid = tmid; 
		this.outDate = outDate; 
		this.notes = notes; 
		this.purpose = purpose; 
		this.inDate = inDate; 
		this.tmType = tmType; 
		this.employeeID = employeeID; 
		this.animals = animals;
	}

	public String getTmid(){
		return this.tmid;
	}
	public void setTmid(String tmid) {
		this.tmid = tmid; 
	}
	public String getOutDate(){
		return this.outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getPurpose(){
		return this.purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose; 
	}
	public String getInDate(){
		return this.inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate; 
	}
	public String getTmType(){
		return this.tmType;
	}
	public void setTmType(String tmType) {
		this.tmType = tmType; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public StringList getAnimals() {
		return animals;
	}
	public void setAnimals(StringList animals) {
		this.animals = animals;
	}
	
}
