package org.ri.se.trace.api.trace;

public class TraceGraph {
	private NodeList nodes;
    private EdgesList edges;
	public NodeList getNodes() {
		return nodes;
	}
	public void setNodes(NodeList nodes) {
		this.nodes = nodes;
	}
	public EdgesList getEdges() {
		return edges;
	}
	public void setEdges(EdgesList edges) {
		this.edges = edges;
	}
}
