package com.rise.vdr.api.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "SCEvidence")
@XmlRootElement
public class SCEvidence{
	@Id
	@Column(name = "eid")
	private String eid;
	private String accessLevel;
	private String resType;
	private String link;
	private String transactionID;
	public SCEvidence (){

	}
	public SCEvidence ( String eid,String accessLevel,String resType,String link,String transactionID){
		this.eid = eid; 
		this.accessLevel = accessLevel; 
		this.resType = resType; 
		this.link = link; 
		this.transactionID = transactionID; 
	}

	public String getEid(){
		return this.eid;
	}
	public void setEid(String eid) {
		this.eid = eid; 
	}
	public String getAccessLevel(){
		return this.accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel; 
	}
	public String getResType(){
		return this.resType;
	}
	public void setResType(String resType) {
		this.resType = resType; 
	}
	public String getLink(){
		return this.link;
	}
	public void setLink(String link) {
		this.link = link; 
	}
	public String getTransactionID(){
		return this.transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID; 
	}
}
