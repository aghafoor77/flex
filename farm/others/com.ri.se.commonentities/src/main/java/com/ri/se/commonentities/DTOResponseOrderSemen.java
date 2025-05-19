package com.ri.se.commonentities;

import java.util.Date;

public class DTOResponseOrderSemen{

	private String osid;
	private String notes;
	private String resDate;
	private String employeeID;
	private String repliedBy;
	private String billingURL;
	public DTOResponseOrderSemen (){

	}
	public DTOResponseOrderSemen ( String osid,String notes,String resDate,String employeeID,String repliedBy,String billingURL){
		this.osid = osid; 
		this.notes = notes; 
		this.resDate = resDate; 
		this.employeeID = employeeID; 
		this.repliedBy = repliedBy; 
		this.billingURL = billingURL; 
	}

	public String getOsid(){
		return this.osid;
	}
	public void setOsid(String osid) {
		this.osid = osid; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getResDate(){
		return this.resDate;
	}
	public void setResDate(String resDate) {
		this.resDate = resDate; 
	}
	public String getEmployeeID(){
		return this.employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID; 
	}
	public String getRepliedBy(){
		return this.repliedBy;
	}
	public void setRepliedBy(String repliedBy) {
		this.repliedBy = repliedBy; 
	}
	public String getBillingURL(){
		return this.billingURL;
	}
	public void setBillingURL(String billingURL) {
		this.billingURL = billingURL; 
	}
}
