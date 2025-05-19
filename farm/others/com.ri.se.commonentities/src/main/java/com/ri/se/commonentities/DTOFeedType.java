package com.ri.se.commonentities;

import java.util.Date;

public class DTOFeedType{

	private String feedName;
	private String creationDate;
	public DTOFeedType (){

	}
	public DTOFeedType ( String feedName,String creationDate){
		this.feedName = feedName; 
		this.creationDate = creationDate; 
	}

	public String getFeedName(){
		return this.feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName; 
	}
	public String getCreationDate(){
		return this.creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate; 
	}
}
