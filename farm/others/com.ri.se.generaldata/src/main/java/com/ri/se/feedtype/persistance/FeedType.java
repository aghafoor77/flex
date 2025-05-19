package com.ri.se.feedtype.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "FeedType")
@XmlRootElement
public class FeedType{
	@Id
	@Column(name = "feedName")
	private String feedName;
	private Date creationDate;
	public FeedType (){

	}
	public FeedType ( String feedName,Date creationDate){
		this.feedName = feedName; 
		this.creationDate = creationDate; 
	}

	public String getFeedName(){
		return this.feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName; 
	}
	public Date getCreationDate(){
		return this.creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate; 
	}
}
