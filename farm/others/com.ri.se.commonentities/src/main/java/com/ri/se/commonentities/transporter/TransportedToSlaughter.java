package com.ri.se.commonentities.transporter;

public class TransportedToSlaughter {
	private com.ri.se.commonentities.transporter.CarrierSlaughterhouse carrierSlaughterhouse;
	private com.ri.se.commonentities.transporter.SHCarrier carrier;
	private TransferedAnimal transferedAnimal;
	private String to;
	private String from;
	
	public CarrierSlaughterhouse getCarrierSlaughterhouse() {
		return carrierSlaughterhouse;
	}
	public void setCarrierSlaughterhouse(CarrierSlaughterhouse carrierSlaughterhouse) {
		this.carrierSlaughterhouse = carrierSlaughterhouse;
	}
	public SHCarrier getCarrier() {
		return carrier;
	}
	public void setCarrier(SHCarrier carrier) {
		this.carrier = carrier;
	}
	public TransferedAnimal getTransferedAnimal() {
		return transferedAnimal;
	}
	public void setTransferedAnimal(TransferedAnimal transferedAnimal) {
		this.transferedAnimal = transferedAnimal;
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
