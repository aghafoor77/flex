package com.ri.se.animalderegister.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "AnimalDeregister")
@XmlRootElement
public class AnimalDeregister{
	@Id
	@Column(name = "animalID")
	private String animalID;
	private Date deregisterDate;
	private String reportBy;
	private String notes;
	private String reportTo;
	private String response;
	private String location;
	private String emailTo;
	private String dcode;
	public AnimalDeregister (){

	}
	public AnimalDeregister ( String animalID,Date deregisterDate,String reportBy,String notes,String reportTo,String response,String location,String emailTo,String dcode){
		this.animalID = animalID; 
		this.deregisterDate = deregisterDate; 
		this.reportBy = reportBy; 
		this.notes = notes; 
		this.reportTo = reportTo; 
		this.response = response; 
		this.location = location; 
		this.emailTo = emailTo; 
		this.dcode = dcode; 
	}

	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public Date getDeregisterDate(){
		return this.deregisterDate;
	}
	public void setDeregisterDate(Date deregisterDate) {
		this.deregisterDate = deregisterDate; 
	}
	public String getReportBy(){
		return this.reportBy;
	}
	public void setReportBy(String reportBy) {
		this.reportBy = reportBy; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getReportTo(){
		return this.reportTo;
	}
	public void setReportTo(String reportTo) {
		this.reportTo = reportTo; 
	}
	public String getResponse(){
		return this.response;
	}
	public void setResponse(String response) {
		this.response = response; 
	}
	public String getLocation(){
		return this.location;
	}
	public void setLocation(String location) {
		this.location = location; 
	}
	public String getEmailTo(){
		return this.emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo; 
	}
	public String getDcode(){
		return this.dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode; 
	}
}
