package com.ri.se.movebullforherd.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "MoveBullForHerd")
@XmlRootElement
public class MoveBullForHerd{
	@Id
	@Column(name = "mb4hid")
	private String mb4hid;
	private String notes;
	private Date entryDate;
	private String groupFemale;
	private String employeeID;
	private String location;
	private String animalID;
	private Date exitDate;
	public MoveBullForHerd (){

	}
	public MoveBullForHerd ( String mb4hid,String notes,Date entryDate,String groupFemale,String employeeID,String location,String animalID,Date exitDate){
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
	public Date getEntryDate(){
		return this.entryDate;
	}
	public void setEntryDate(Date entryDate) {
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
	public Date getExitDate(){
		return this.exitDate;
	}
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate; 
	}
}
