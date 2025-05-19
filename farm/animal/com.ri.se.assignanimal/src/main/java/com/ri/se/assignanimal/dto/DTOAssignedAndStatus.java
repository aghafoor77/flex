package com.ri.se.assignanimal.dto;

import java.util.Date;

public class DTOAssignedAndStatus {
	private String aaid;
	private Date assignedDate;
	private String notes;
	private String examiner;
	private String action;
	private String aasid;
	private Date submissionDate;
	private String animals;
	private String reportReference;
	private String reportSubmitted;

	public String getAaid() {
		return aaid;
	}

	public void setAaid(String aaid) {
		this.aaid = aaid;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAasid() {
		return aasid;
	}

	public void setAasid(String aasid) {
		this.aasid = aasid;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getAnimals() {
		return animals;
	}

	public void setAnimals(String animals) {
		this.animals = animals;
	}

	public String getReportReference() {
		return reportReference;
	}

	public void setReportReference(String reportReference) {
		this.reportReference = reportReference;
	}

	public String getReportSubmitted() {
		return reportSubmitted;
	}

	public void setReportSubmitted(String reportSubmitted) {
		this.reportSubmitted = reportSubmitted;
	}

}
