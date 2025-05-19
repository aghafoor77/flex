package com.ri.se.commonentities;

import java.util.Date;

public class DTOAnimalExamination{

	private String aeid;
	private String notes;
	private String examinationDate;
	private String refnumber;
	private String refType;
	private String sensorData;
	private String employeeID;
	private String animalID;
	private String extepctedDate;
	private String status;
	public DTOAnimalExamination (){

	}
	public DTOAnimalExamination ( String aeid,String notes,String examinationDate,String refnumber,String refType,String sensorData,String employeeID,String animalID,String extepctedDate,String status){
		this.aeid = aeid; 
		this.notes = notes; 
		this.examinationDate = examinationDate; 
		this.refnumber = refnumber; 
		this.refType = refType; 
		this.sensorData = sensorData; 
		this.employeeID = employeeID; 
		this.animalID = animalID; 
		this.extepctedDate = extepctedDate; 
		this.status = status; 
	}

	public String getAeid(){
		return this.aeid;
	}
	public void setAeid(String aeid) {
		this.aeid = aeid; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getExaminationDate(){
		return this.examinationDate;
	}
	public void setExaminationDate(String examinationDate) {
		this.examinationDate = examinationDate; 
	}
	public String getRefnumber(){
		return this.refnumber;
	}
	public void setRefnumber(String refnumber) {
		this.refnumber = refnumber; 
	}
	public String getRefType(){
		return this.refType;
	}
	public void setRefType(String refType) {
		this.refType = refType; 
	}
	public String getSensorData(){
		return this.sensorData;
	}
	public void setSensorData(String sensorData) {
		this.sensorData = sensorData; 
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
	public String getExtepctedDate(){
		return this.extepctedDate;
	}
	public void setExtepctedDate(String extepctedDate) {
		this.extepctedDate = extepctedDate; 
	}
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
}
