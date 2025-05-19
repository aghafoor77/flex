package com.contract.traceability.vdr;

import java.util.List;

public class Evidence {

	private String eid;
	private String transactionID;
	private String link;
	private String resType;
	private List<SharedSecret> sharedSecrets;
	private String accessLevel;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public List<SharedSecret> getSharedSecrets() {
		return sharedSecrets;
	}

	public void setSharedSecrets(List<SharedSecret> sharedSecrets) {
		this.sharedSecrets = sharedSecrets;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	
}
