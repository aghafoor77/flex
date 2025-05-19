package com.ri.se.commonentities;

import java.util.Date;

public class DTOFeedPattern{

	private String fpid;
	private String certiOfIngredients;
	private String notes;
	private String feedName;
	private String price;
	private String feedType;
	private String percentage;
	private String creationDate;
	private String foodSource;
	private double volume; 
	public DTOFeedPattern (){

	}
	public DTOFeedPattern ( String fpid,String certiOfIngredients,String notes,String feedName,String price,String feedType,String percentage,String creationDate,String foodSource, double volume){
		this.fpid = fpid; 
		this.certiOfIngredients = certiOfIngredients; 
		this.notes = notes; 
		this.feedName = feedName; 
		this.price = price; 
		this.feedType = feedType; 
		this.percentage = percentage; 
		this.creationDate = creationDate; 
		this.foodSource = foodSource; 
		this.volume = volume;
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
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getCreationDate(){
		return this.creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate; 
	}
	public String getFoodSource(){
		return this.foodSource;
	}
	public void setFoodSource(String foodSource) {
		this.foodSource = foodSource; 
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}	
}
