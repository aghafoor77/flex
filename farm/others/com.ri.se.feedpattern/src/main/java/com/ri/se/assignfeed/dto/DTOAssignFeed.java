package com.ri.se.assignfeed.dto;

import java.util.Date;

public class DTOAssignFeed{

	private String afid;
	private String assignedDate;
	private String assignedBy;
	private String fpid;
	private String assignedTo;
	public DTOAssignFeed (){

	}
	public DTOAssignFeed ( String afid,String assignedDate,String assignedBy,String fpid,String assignedTo){
		this.afid = afid; 
		this.assignedDate = assignedDate; 
		this.assignedBy = assignedBy; 
		this.fpid = fpid; 
		this.assignedTo = assignedTo; 
	}

	public String getAfid(){
		return this.afid;
	}
	public void setAfid(String afid) {
		this.afid = afid; 
	}
	public String getAssignedDate(){
		return this.assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate; 
	}
	public String getAssignedBy(){
		return this.assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy; 
	}
	public String getFpid(){
		return this.fpid;
	}
	public void setFpid(String fpid) {
		this.fpid = fpid; 
	}
	public String getAssignedTo(){
		return this.assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo; 
	}
}
