package centralhostregistration.dto;

public class ConfigUnit {
	
	private String url;
	private String required;
	private String key;
	private String value;
	private int port;
	private String healthCheck;
	private String type;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHealthCheck() {
		return healthCheck;
	}
	public void setHealthCheck(String healthCheck) {
		this.healthCheck = healthCheck;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
