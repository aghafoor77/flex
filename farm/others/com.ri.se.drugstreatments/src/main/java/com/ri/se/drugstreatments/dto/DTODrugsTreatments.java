package com.ri.se.drugstreatments.dto;

import java.util.Date;

public class DTODrugsTreatments{

	private String dtid;
	private String assignedDate;
	private String reason;
	private String drungs;
	private String refnumber;
	private String examinedBy;
	private String animalID;
	private String reftype;
	//private String type;
	public DTODrugsTreatments (){

	}
	public DTODrugsTreatments ( String dtid,String assignedDate,String reason,String drungs,String refnumber,String examinedBy,String animalID,String reftype){
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
	public String getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
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
