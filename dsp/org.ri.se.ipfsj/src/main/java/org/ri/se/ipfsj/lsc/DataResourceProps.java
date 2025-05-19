package org.ri.se.ipfsj.lsc;

public class DataResourceProps {
	private String path;

	public enum SecurityLevel {
		NONE("NONE"), SIGNED("SIGNED"), ENVELOPED("ENVELOPED"), SIGNEDANDENVELOPED("SIGNEDANDENVELOPED");

		String value;

		SecurityLevel(String _value) {
			value = _value;
		}

		public String value() {
			return value;
		}
	}

	public String ipfsCID;
	public SecurityLevel securityLevel;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getIpfsCID() {
		return ipfsCID;
	}

	public void setIpfsCID(String ipfsCID) {
		this.ipfsCID = ipfsCID;
	}

}
