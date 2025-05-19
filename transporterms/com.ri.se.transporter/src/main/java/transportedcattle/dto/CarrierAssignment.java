package transportedcattle.dto;

public class CarrierAssignment {
	
	private String cid;
	private String salughterhousename;
	private String location;
	private String startDate;
	private String endDate;
	private String to;
	private String from;
	private DTOStringList animals;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getSalughterhousename() {
		return salughterhousename;
	}
	public void setSalughterhousename(String salughterhousename) {
		this.salughterhousename = salughterhousename;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public DTOStringList getAnimals() {
		return animals;
	}
	public void setAnimals(DTOStringList animals) {
		this.animals = animals;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	

}
