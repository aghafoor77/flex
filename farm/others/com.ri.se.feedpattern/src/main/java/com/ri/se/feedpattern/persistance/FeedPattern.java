package com.ri.se.feedpattern.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "FeedPattern")
@XmlRootElement
public class FeedPattern{
	@Id
	@Column(name = "fpid")
	private String fpid;
	private String certiOfIngredients;
	private String notes;
	private String feedName;
	private String pricae;
	private String feedType;
	private String percentage;
	private Date creationDate;
	private String foodSource;
	public FeedPattern (){

	}
	public FeedPattern ( String fpid,String certiOfIngredients,String notes,String feedName,String pricae,String feedType,String percentage,Date creationDate,String foodSource){
		this.fpid = fpid; 
		this.certiOfIngredients = certiOfIngredients; 
		this.notes = notes; 
		this.feedName = feedName; 
		this.pricae = pricae; 
		this.feedType = feedType; 
		this.percentage = percentage; 
		this.creationDate = creationDate; 
		this.foodSource = foodSource; 
	}

	public String getFpid(){
		return this.fpid;
	}
	public void setFpid(String fpid) {
		this.fpid = fpid; 
	}
	public String getCertiOfIngredients(){
		return this.certiOfIngredients;
	}
	public void setCertiOfIngredients(String certiOfIngredients) {
		this.certiOfIngredients = certiOfIngredients; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getFeedName(){
		return this.feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName; 
	}
	public String getPricae(){
		return this.pricae;
	}
	public void setPricae(String pricae) {
		this.pricae = pricae; 
	}
	public String getFeedType(){
		return this.feedType;
	}
	public void setFeedType(String feedType) {
		this.feedType = feedType; 
	}
	public String getPercentage(){
		return this.percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage; 
	}
	public Date getCreationDate(){
		return this.creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate; 
	}
	public String getFoodSource(){
		return this.foodSource;
	}
	public void setFoodSource(String foodSource) {
		this.foodSource = foodSource; 
	}
}
