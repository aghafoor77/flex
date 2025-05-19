package com.ri.se.semenutilization.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "SemenUtilization")
@XmlRootElement
public class SemenUtilization{
	@Id
	@Column(name = "suid")
	private String suid;
	private Date insemationDate;
	private String notes;
	private String employeeID;
	private String osid;
	private String animalID;
	public SemenUtilization (){

	}
	public SemenUtilization ( String suid,Date insemationDate,String notes,String employeeID,String osid,String animalID){
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
	public Date getInsemationDate(){
		return this.insemationDate;
	}
	public void setInsemationDate(Date insemationDate) {
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
