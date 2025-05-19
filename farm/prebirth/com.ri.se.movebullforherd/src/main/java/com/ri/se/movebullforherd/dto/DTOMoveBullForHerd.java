package com.ri.se.movebullforherd.dto;

import java.util.Date;

public class DTOMoveBullForHerd{

	private String mb4hid;
	private String notes;
	private String entryDate;
	private String groupFemale;
	private String employeeID;
	private String location;
	private String animalID;
	private String exitDate;
	public DTOMoveBullForHerd (){

	}
	public DTOMoveBullForHerd ( String mb4hid,String notes,String entryDate,String groupFemale,String employeeID,String location,String animalID,String exitDate){
		this.mb4hid = mb4hid; 
		this.notes = notes; 
		this.entryDate = entryDate; 
		this.groupFemale = groupFemale; 
		this.employeeID = employeeID; 
		this.location = location; 
		this.animalID = animalID; 
		this.exitDate = exitDate; 
	}

	public String getMb4hid(){
		return this.mb4hid;
	}
	public void setMb4hid(String mb4hid) {
		this.mb4hid = mb4hid; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getEntryDate(){
		return this.entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate; 
	}
	public String getGroupFemale(){
		return this.groupFemale;
	}
	public void setGroupFemale(String groupFemale) {
		this.groupFemale = groupFemale; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getLocation(){
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getExitDate(){
		return this.exitDate;
	}
	public void setExitDate(String exitDate) {
		this.exitDate = exitDate; 
	}
}
