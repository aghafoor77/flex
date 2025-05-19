package com.ri.se.assignanimal.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "AssignAnimal")
@XmlRootElement
public class AssignAnimal{
	@Id
	@Column(name = "aaid")
	private String aaid;
	private Date assignedDate;
	private String notes;
	private String examiner;
	private String action;
	private Date submissionDate;
	private String animals;
	private String reportReference;
	private String reportSubmitted;
	public AssignAnimal (){

	}
	public AssignAnimal ( String aaid,Date assignedDate,String notes,String examiner,String action,Date submissionDate,String animals,String reportReference,String reportSubmitted){
		this.aaid = aaid; 
		this.assignedDate = assignedDate; 
		this.notes = notes; 
		this.examiner = examiner; 
		this.action = action; 
		this.submissionDate = submissionDate; 
		this.animals = animals; 
		this.reportReference = reportReference; 
		this.reportSubmitted = reportSubmitted; 
	}

	public String getAaid(){
		return this.aaid;
	}
	public void setAaid(String aaid) {
		this.aaid = aaid; 
	}
	public Date getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
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
