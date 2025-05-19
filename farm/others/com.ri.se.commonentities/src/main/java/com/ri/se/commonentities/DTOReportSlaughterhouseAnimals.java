package com.ri.se.commonentities;

import java.util.Date;

public class DTOReportSlaughterhouseAnimals{

	private String rsaid;
	private String selectionDate;
	private String employeeID;
	private String rid;
	private String animalID;
	public DTOReportSlaughterhouseAnimals (){

	}
	public DTOReportSlaughterhouseAnimals ( String rsaid,String selectionDate,String employeeID,String rid,String animalID){
		this.rsaid = rsaid; 
		this.selectionDate = selectionDate; 
		this.employeeID = employeeID; 
		this.rid = rid; 
		this.animalID = animalID; 
	}

	public String getRsaid(){
		return this.rsaid;
	}
	public void setRsaid(String rsaid) {
		this.rsaid = rsaid; 
	}
	public String getSelectionDate(){
		return this.selectionDate;
	}
	public void setSelectionDate(String selectionDate) {
		this.selectionDate = selectionDate; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getRid(){
		return this.rid;
	}
	public void setRid(String rid) {
		this.rid = rid; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
}
