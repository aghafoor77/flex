package com.rise.vdr.api.transaction;

public class EvidenceObject {
	private String evidenceType;
	private Object evidence;
	private String uniqueNo;
	public String getEvidenceType() {
		return evidenceType;
	}
	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}
	public Object getEvidence() {
		return evidence;
	}
	public void setEvidence(Object evidence) {
		this.evidence = evidence;
	}
	public String getUniqueNo() {
		return uniqueNo;
	}
	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	
}
