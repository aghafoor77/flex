package com.rise.vdr.api.transaction;

public class EvidenceObject {
	private String evidenceType;
	private Object evidence;
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
}
