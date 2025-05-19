package com.ri.se.assignanimal.dto;

import java.util.ArrayList;
import java.util.List;

public class DTOAssignAnimal{

	private String aaid;
	private String assignedDate;
	private String notes;
	private String examiner;
	private String action;
	private String submissionDate;
	private List<String> animals;
	private String reportReference;
	private String reportSubmitted;
	public DTOAssignAnimal (){

	}
	public DTOAssignAnimal ( String aaid,String assignedDate,String notes,String examiner,String action,String submissionDate,String animals_,String reportReference,String reportSubmitted){
		this.aaid = aaid; 
		this.assignedDate = assignedDate; 
		this.notes = notes; 
		this.examiner = examiner; 
		this.action = action; 
		this.submissionDate = submissionDate; 
		this.animals = new ArrayList<String>();
		String an []= animals_.split(",");
		for(String a: an ) {
			this.animals .add(a);
		}
		this.reportReference = reportReference; 
		this.reportSubmitted = reportSubmitted; 
	}

	public String getAaid(){
		return this.aaid;
	}
	public void setAaid(String aaid) {
		this.aaid = aaid; 
	}
	public String getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getExaminer(){
		return this.examiner;
	}
	public void setExaminer(String examiner) {
		this.examiner = examiner; 
	}
	public String getAction(){
		return this.action;
	}
	public void setAction(String action) {
		this.action = action; 
	}
	public String getSubmissionDate(){
		return this.submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate; 
	}
	public List<String> getAnimals(){
		return this.animals;
	}
	public void setAnimals(List<String> animals) {
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
