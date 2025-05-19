package com.rise.vdr.api.utils;

import com.rise.vdr.api.transaction.EvidenceObject;

public class ALEvidenceObject {
	private EvidenceObject evidence;
	private String accessLevel;
	public EvidenceObject getEvidence() {
		return evidence;
	}
	public void setEvidence(EvidenceObject evidence) {
		this.evidence = evidence;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	

}
