package com.ri.se.reportslaughterhouse.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "ReportSlaughterhouse")
@XmlRootElement
public class ReportSlaughterhouse{
	@Id
	@Column(name = "rid")
	private String rid;
	private String notes;
	private String slaughterhousename;
	private String response;
	private String sex;
	private int numberofanimals;
	private Date reportingDate;
	private String employeeID;
	private String slaughterhousecontact;
	private String age;
	private String breed;
	public ReportSlaughterhouse (){

	}
	public ReportSlaughterhouse ( String rid,String notes,String slaughterhousename,String response,String sex,int numberofanimals,Date reportingDate,String employeeID,String slaughterhousecontact,String age,String breed){
		this.rid = rid; 
		this.notes = notes; 
		this.slaughterhousename = slaughterhousename; 
		this.response = response; 
		this.sex = sex; 
		this.numberofanimals = numberofanimals; 
		this.reportingDate = reportingDate; 
		this.employeeID = employeeID; 
		this.slaughterhousecontact = slaughterhousecontact; 
		this.age = age; 
		this.breed = breed; 
	}

	public String getRid(){
		return this.rid;
	}
	public void setRid(String rid) {
		this.rid = rid; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getSlaughterhousename(){
		return this.slaughterhousename;
	}
	public void setSlaughterhousename(String slaughterhousename) {
		this.slaughterhousename = slaughterhousename; 
	}
	public String getResponse(){
		return this.response;
	}
	public void setResponse(String response) {
		this.response = response; 
	}
	public String getSex(){
		return this.sex;
	}
	public void setSex(String sex) {
		this.sex = sex; 
	}
	public int getNumberofanimals(){
		return this.numberofanimals;
	}
	public void setNumberofanimals(int numberofanimals) {
		this.numberofanimals = numberofanimals; 
	}
	public Date getReportingDate(){
		return this.reportingDate;
	}
	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getSlaughterhousecontact(){
		return this.slaughterhousecontact;
	}
	public void setSlaughterhousecontact(String slaughterhousecontact) {
		this.slaughterhousecontact = slaughterhousecontact; 
	}
	public String getAge(){
		return this.age;
	}
	public void setAge(String age) {
		this.age = age; 
	}
	public String getBreed(){
		return this.breed;
	}
	public void setBreed(String breed) {
		this.breed = breed; 
	}
}
