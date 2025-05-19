package analytics.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Analytics")
@XmlRootElement
public class Analytics{
	@Id
	@Column(name = "AID")
	private String AID;
	private String ADG;
	public Analytics (){

	}
	public Analytics ( String AID,String ADG){
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
