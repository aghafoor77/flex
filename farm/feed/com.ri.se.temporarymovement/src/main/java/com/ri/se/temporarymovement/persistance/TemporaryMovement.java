package com.ri.se.temporarymovement.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "TemporaryMovement")
@XmlRootElement
public class TemporaryMovement{
	@Id
	@Column(name = "tmid")
	private String tmid;
	private Date outDate;
	private String notes;
	private String purpose;
	private Date inDate;
	private String tmType;
	private String employeeID;
	public TemporaryMovement (){

	}
	public TemporaryMovement ( String tmid,Date outDate,String notes,String purpose,Date inDate,String tmType,String employeeID){
		this.tmid = tmid; 
		this.outDate = outDate; 
		this.notes = notes; 
		this.purpose = purpose; 
		this.inDate = inDate; 
		this.tmType = tmType; 
		this.employeeID = employeeID; 
	}

	public String getTmid(){
		return this.tmid;
	}
	public void setTmid(String tmid) {
		this.tmid = tmid; 
	}
	public Date getOutDate(){
		return this.outDate;
	}
	public void setOutDate(Date outDate) {
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
	public Date getInDate(){
		return this.inDate;
	}
	public void setInDate(Date inDate) {
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
}
