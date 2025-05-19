package ri.se.trace.persistant.common;

public class AttachedResourceCO {
	private String ipfshash;
	private String owner;
	private String name;
	private String identity;
	private String type;
	private String securityLevel;
	private String uploadTime;
	private String description;

	public AttachedResourceCO() {
		super();
	}

	public AttachedResourceCO(String ipfshash, String owner, String name, String identity, String type, String securityLevel, String uploadTime,
			String description) {
		super();
		this.ipfshash = ipfshash;
		this.owner = owner;
		this.name = name;
		this.identity = identity;
		this.type = type;
		this.securityLevel =securityLevel;
		this.uploadTime = uploadTime;
		this.description = description;
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

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
