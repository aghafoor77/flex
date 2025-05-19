package org.ri.se.trace.api.trace;

public class Edge {
	private int from;
	private int to;
	private String arrows = "to";
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public String getArrows() {
		return arrows;
	}
	public void setArrows(String arrows) {
		this.arrows = arrows;
	}
	
	
}
