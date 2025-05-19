package ri.se.trace.persistant;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "AttachedResource")
@XmlRootElement
public class AttachedResource {

	@Id
	@Column(name = "ipfshash")
	private String ipfshash;
	private String owner;
	private String name;
	private String identity;
	private String type;
	private String securityLevel;
	private Date uploadTime;
	private String description;
	private String status;

	public AttachedResource() {
		super();
	}

	public AttachedResource(String ipfshash, String owner, String name, String identity, String type,String securityLevel, Date uploadTime,
			String description, String status) {
		super();
		this.ipfshash = ipfshash;
		this.owner = owner;
		this.name = name;
		this.identity = identity;
		this.type = type;
		this.securityLevel = securityLevel;
		this.uploadTime = uploadTime;
		this.description = description;
		this.status = status;
	}

	public String getIpfshash() {
		return ipfshash;
	}

	public void setIpfshash(String ipfshash) {
		this.ipfshash = ipfshash;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
