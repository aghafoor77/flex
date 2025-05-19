package com.ri.se.reportslaughterhouseanimals.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "ReportSlaughterhouseAnimals")
@XmlRootElement
public class ReportSlaughterhouseAnimals{
	@Id
	@Column(name = "rsaid")
	private String rsaid;
	private Date selectionDate;
	private String employeeID;
	private String rid;
	private String animalID;
	public ReportSlaughterhouseAnimals (){

	}
	public ReportSlaughterhouseAnimals ( String rsaid,Date selectionDate,String employeeID,String rid,String animalID){
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
	public Date getSelectionDate(){
		return this.selectionDate;
	}
	public void setSelectionDate(Date selectionDate) {
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
