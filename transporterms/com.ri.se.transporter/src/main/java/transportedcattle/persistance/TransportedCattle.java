package transportedcattle.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "TransportedCattle")
@XmlRootElement
public class TransportedCattle{
	@Id
	@Column(name = "TCID")
	private String TCID;
	private String enteranceRecord;
	private String notes;
	private String GHEID;
	private String otherData;
	private String destination;
	private double temperature;
	private Date departDate;
	private String transportType;
	private String source;
	private String animalID;
	private String carriernumber;
	public TransportedCattle (){

	}
	public TransportedCattle ( String TCID,String enteranceRecord,String notes,String GHEID,String otherData,String destination,double temperature,Date departDate,String transportType,String source,String animalID,String carriernumber){
		this.TCID = TCID; 
		this.enteranceRecord = enteranceRecord; 
		this.notes = notes; 
		this.GHEID = GHEID; 
		this.otherData = otherData; 
		this.destination = destination; 
		this.temperature = temperature; 
		this.departDate = departDate; 
		this.transportType = transportType; 
		this.source = source; 
		this.animalID = animalID; 
		this.carriernumber = carriernumber; 
	}

	public String getTCID(){
		return this.TCID;
	}
	public void setTCID(String TCID) {
		this.TCID = TCID; 
	}
	public String getEnteranceRecord(){
		return this.enteranceRecord;
	}
	public void setEnteranceRecord(String enteranceRecord) {
		this.enteranceRecord = enteranceRecord; 
	}
	public String getNotes(){
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes; 
	}
	public String getGHEID(){
		return this.GHEID;
	}
	public void setGHEID(String GHEID) {
		this.GHEID = GHEID; 
	}
	public String getOtherData(){
		return this.otherData;
	}
	public void setOtherData(String otherData) {
		this.otherData = otherData; 
	}
	public String getDestination(){
		return this.destination;
	}
	public void setDestination(String destination) {
		this.destination = destination; 
	}
	public double getTemperature(){
		return this.temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature; 
	}
	public Date getDepartDate(){
		return this.departDate;
	}
	public void setDepartDate(Date departDate) {
		this.departDate = departDate; 
	}
	public String getTransportType(){
		return this.transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType; 
	}
	public String getSource(){
		return this.source;
	}
	public void setSource(String source) {
		this.source = source; 
	}
	public String getAnimalID(){
		return this.animalID;
	}
	public void setAnimalID(String animalID) {
		this.animalID = animalID; 
	}
	public String getCarriernumber(){
		return this.carriernumber;
	}
	public void setCarriernumber(String carriernumber) {
		this.carriernumber = carriernumber; 
	}
}
