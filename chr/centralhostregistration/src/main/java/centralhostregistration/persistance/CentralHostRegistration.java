package centralhostregistration.persistance;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CentralHostRegistration")
@XmlRootElement
public class CentralHostRegistration {
	@Id
	@Column(name = "name")
	private String name;
	private String address;
	private int port;
	private String healthCheck;
	private Date registrationDate;
	private String status;
	private int hb;
	private String type;

	public CentralHostRegistration() {

	}

	public CentralHostRegistration(String name, String address, int port, String healthCheck, Date registrationDate,
			String status,int hb,String type) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.healthCheck = healthCheck;
		this.registrationDate = registrationDate;
		this.status = status;
		this.hb = hb;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHealthCheck() {
		return this.healthCheck;
	}

	public void setHealthCheck(String healthCheck) {
		this.healthCheck = healthCheck;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHb() {
		return hb;
	}

	public void setHb(int hb) {
		this.hb = hb;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
