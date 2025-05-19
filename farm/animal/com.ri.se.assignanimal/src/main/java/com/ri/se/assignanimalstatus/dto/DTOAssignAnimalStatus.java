package com.ri.se.assignanimalstatus.dto;

import java.util.Date;

public class DTOAssignAnimalStatus{

	private String aasid;
	private String aaid;
	private String submissionDate;
	private String animals;
	private String reportReference;
	private String reportSubmitted;
	public DTOAssignAnimalStatus (){

	}
	public DTOAssignAnimalStatus ( String aasid,String aaid,String submissionDate,String animals,String reportReference,String reportSubmitted){
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
	public String getSubmissionDate(){
		return this.submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
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
