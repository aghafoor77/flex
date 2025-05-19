package com.ri.se.assignanimalstatus.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "AssignAnimalStatus")
@XmlRootElement
public class AssignAnimalStatus{
	@Id
	@Column(name = "aasid")
	private String aasid;
	private String aaid;
	private Date submissionDate;
	private String animals;
	private String reportReference;
	private String reportSubmitted;
	public AssignAnimalStatus (){

	}
	public AssignAnimalStatus ( String aasid,String aaid,Date submissionDate,String animals,String reportReference,String reportSubmitted){
		this.aasid = aasid; 
		this.aaid = aaid; 
		this.submissionDate = submissionDate; 
		this.animals = animals; 
		this.reportReference = reportReference; 
		this.reportSubmitted = reportSubmitted; 
	}

	public String getAasid(){
		return this.aasid;
	}
	public void setAasid(String aasid) {
		this.aasid = aasid; 
	}
	public String getAaid(){
		return this.aaid;
	}
	public void setAaid(String aaid) {
		this.aaid = aaid; 
	}
	public Date getSubmissionDate(){
		return this.submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate; 
	}
	public String getAnimals(){
		return this.animals;
	}
	public void setAnimals(String animals) {
		this.animals = animals; 
	}
	public String getReportReference(){
		return this.reportReference;
	}
	public void setReportReference(String reportReference) {
		this.reportReference = reportReference; 
	}
	public String getReportSubmitted(){
		return this.reportSubmitted;
	}
	public void setReportSubmitted(String reportSubmitted) {
		this.reportSubmitted = reportSubmitted; 
	}
}
