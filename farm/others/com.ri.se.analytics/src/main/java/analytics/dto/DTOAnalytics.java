package analytics.dto;

import java.util.Date;

public class DTOAnalytics{

	private String AID;
	private String ADG;
	public DTOAnalytics (){

	}
	public DTOAnalytics ( String AID,String ADG){
		this.AID = AID; 
		this.ADG = ADG; 
	}

	public String getAID(){
		return this.AID;
	}
	public void setAID(String AID) {
		this.AID = AID; 
	}
	public String getADG(){
		return this.ADG;
	}
	public void setADG(String ADG) {
		this.ADG = ADG; 
	}
}
