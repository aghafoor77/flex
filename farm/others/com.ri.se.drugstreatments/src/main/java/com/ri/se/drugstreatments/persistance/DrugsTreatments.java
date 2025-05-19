package com.ri.se.drugstreatments.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "DrugsTreatments")
@XmlRootElement
public class DrugsTreatments{
	@Id
	@Column(name = "dtid")
	private String dtid;
	private Date assignedDate;
	private String reason;
	private String drungs;
	private String refnumber;
	private String examinedBy;
	private String animalID;
	private String reftype;
	public DrugsTreatments (){

	}
	public DrugsTreatments ( String dtid,Date assignedDate,String reason,String drungs,String refnumber,String examinedBy,String animalID,String reftype){
		this.dtid = dtid; 
		this.assignedDate = assignedDate; 
		this.reason = reason; 
		this.drungs = drungs; 
		this.refnumber = refnumber; 
		this.examinedBy = examinedBy; 
		this.animalID = animalID; 
		this.reftype = reftype; 
	}

	public String getDtid(){
		return this.dtid;
	}
	public void setDtid(String dtid) {
		this.dtid = dtid; 
	}
	public Date getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate; 
	}
	public String getReason(){
		return this.reason;
	}
	public void setReason(String reason) {
		this.reason = reason; 
	}
	public String getDrungs(){
		return this.drungs;
	}
	public void setDrungs(String drungs) {
		this.drungs = drungs; 
	}
	public String getRefnumber(){
		return this.refnumber;
	}
	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber; 
	}
	public String getExaminedBy(){
		return this.examinedBy;
	}
	public void setExaminedBy(String examinedBy) {
		this.examinedBy = examinedBy; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getReftype(){
		return this.reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype; 
	}
}
