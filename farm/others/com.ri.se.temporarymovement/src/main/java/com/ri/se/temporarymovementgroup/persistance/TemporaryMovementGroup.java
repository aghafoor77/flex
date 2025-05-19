package com.ri.se.temporarymovementgroup.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "TemporaryMovementGroup")
@XmlRootElement
public class TemporaryMovementGroup{
	@Id
	@Column(name = "tmgid")
	private String tmgid;
	private Date outDate;
	private String tmid;
	private Date inDate;
	private String employeeID;
	private String animalID;
	public TemporaryMovementGroup (){

	}
	public TemporaryMovementGroup ( String tmgid,Date outDate,String tmid,Date inDate,String employeeID,String animalID){
		this.tmgid = tmgid; 
		this.outDate = outDate; 
		this.tmid = tmid; 
		this.inDate = inDate; 
		this.employeeID = employeeID; 
		this.animalID = animalID; 
	}

	public String getTmgid(){
		return this.tmgid;
	}
	public void setTmgid(String tmgid) {
		this.tmgid = tmgid; 
	}
	public Date getOutDate(){
		return this.outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate; 
	}
	public String getTmid(){
		return this.tmid;
	}
	public void setTmid(String tmid) {
		this.tmid = tmid; 
	}
	public Date getInDate(){
		return this.inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
}
