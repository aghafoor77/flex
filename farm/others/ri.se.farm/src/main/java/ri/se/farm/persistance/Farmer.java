package ri.se.farm.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "Farmer")
@XmlRootElement
public class Farmer{
	@Id
	@Column(name = "resourceId")
	private String resourceId;
	private String country;
	private String role;
	private String street;
	private String contact;
	private String name;
	private String postcode;
	private String county;
	private String municipality;
	private String farmId;
	private String email;
	public Farmer (){

	}
	public Farmer ( String resourceId,String country,String role,String street,String contact,String name,String postcode,String county,String municipality,String farmId,String email){
		this.resourceId = resourceId; 
		this.country = country; 
		this.role = role; 
		this.street = street; 
		this.contact = contact; 
		this.name = name; 
		this.postcode = postcode; 
		this.county = county; 
		this.municipality = municipality; 
		this.farmId = farmId; 
		this.email = email; 
	}

	public String getResourceId(){
		return this.resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId; 
	}
	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country; 
	}
	public String getRole(){
		return this.role;
	}
	public void setRole(String role) {
		this.role = role; 
	}
	public String getStreet(){
		return this.street;
	}
	public void setStreet(String street) {
		this.street = street; 
	}
	public String getContact(){
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact; 
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name) {
		this.name = name; 
	}
	public String getPostcode(){
		return this.postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode; 
	}
	public String getCounty(){
		return this.county;
	}
	public void setCounty(String county) {
		this.county = county; 
	}
	public String getMunicipality(){
		return this.municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality; 
	}
	public String getFarmId(){
		return this.farmId;
	}
	public void setFarmId(String farmId) {
		this.farmId = farmId; 
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email; 
	}
}
