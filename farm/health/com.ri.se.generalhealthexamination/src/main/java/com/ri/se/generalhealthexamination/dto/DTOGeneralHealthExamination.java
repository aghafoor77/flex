package com.ri.se.generalhealthexamination.dto;

import java.util.Date;

public class DTOGeneralHealthExamination{

	private String gaheid;
	private String observer;
	private String wound;
	private String notes;
	private String notation;
	private String temperature;
	private String infections;
	private String lameness;
	private String swelling;
	private String gheDate;
	private String animalID;
	private String weak;
	private String reportType;
	private String assignedStatusId;

	
	
	public DTOGeneralHealthExamination (){

	}
	public DTOGeneralHealthExamination ( String gaheid,String observer,String wound,String notes,String notation,String temperature,String infections,String lameness,String swelling,String gheDate,String animalID,String weak, String reportType, String assignedStatusId){
		this.gaheid = gaheid; 
		this.observer = observer; 
		this.wound = wound; 
		this.notes = notes; 
		this.notation = notation; 
		this.temperature = temperature; 
		this.infections = infections; 
		this.lameness = lameness; 
		this.swelling = swelling; 
		this.gheDate = gheDate; 
		this.animalID = animalID; 
		this.weak = weak; 
		this.reportType = reportType;
		this.assignedStatusId =assignedStatusId;
	}

	public String getGaheid(){
		return this.gaheid;
	}
	public void setGaheid(String gaheid) {
		this.gaheid = gaheid; 
	}
	public String getObserver(){
		return this.observer;
	}
	public void setObserver(String observer) {
		this.observer = observer; 
	}
	public String getWound(){
		return this.wound;
	}
	public void setWound(String wound) {
		this.wound = wound; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getNotation(){
		return this.notation;
	}
	public void setNotation(String notation) {
		this.notation = notation; 
	}
	public String getTemperature(){
		return this.temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature; 
	}
	public String getInfections(){
		return this.infections;
	}
	public void setInfections(String infections) {
		this.infections = infections; 
	}
	public String getLameness(){
		return this.lameness;
	}
	public void setLameness(String lameness) {
		this.lameness = lameness; 
	}
	public String getSwelling(){
		return this.swelling;
	}
	public void setSwelling(String swelling) {
		this.swelling = swelling; 
	}
	public String getGheDate(){
		return this.gheDate;
	}
	public void setGheDate(String gheDate) {
		this.gheDate = gheDate; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getWeak(){
		return this.weak;
	}
	public void setWeak(String weak) {
		this.weak = weak; 
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getAssignedStatusId() {
		return assignedStatusId;
	}
	public void setAssignedStatusId(String assignedStatusId) {
		this.assignedStatusId = assignedStatusId;
	}	
	
}
