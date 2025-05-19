package com.ri.se.responseordersemen.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "ResponseOrderSemen")
@XmlRootElement
public class ResponseOrderSemen{
	@Id
	@Column(name = "osid")
	private String osid;
	private String notes;
	private Date resDate;
	private String employeeID;
	private String repliedBy;
	private String billingURL;
	public ResponseOrderSemen (){

	}
	public ResponseOrderSemen ( String osid,String notes,Date resDate,String employeeID,String repliedBy,String billingURL){
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
	public Date getResDate(){
		return this.resDate;
	}
	public void setResDate(Date resDate) {
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
