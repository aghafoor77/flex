package org.ri.se.vt.v3;

import java.util.ArrayList;
import java.util.HashMap;

import org.ri.se.vt.v3.graph.Edge;
import org.ri.se.vt.v3.graph.EdgesList;
import org.ri.se.vt.v3.graph.Node;
import org.ri.se.vt.v3.graph.NodeList;
import org.ri.se.vt.v3.graph.TraceGraph;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VTUtils {

	String del = ":";

	public ArrayList<String> fromFormated(String _parents) {

		ArrayList<String> arrayParents = new ArrayList<String>();
		String[] parents = _parents.split(del);
		for (String parent : parents) {
			if (parent != null && parent.length() != 0)
				arrayParents.add(parent.trim());
		}
		return arrayParents;
	}

	public String toFormated(ArrayList<String> parents) {

		if(parents.size() == 0) {
			return "";
		}
		String formatedParents = "";
		for (String p : parents) {
			if (p != null && p.length() != 0)
				formatedParents += (p.trim()) + del;
		}
		formatedParents = formatedParents.substring(0, formatedParents.length() - 1);
		return formatedParents;
	}

	public static void main(String arg[]) throws Exception {
		new VTUtils().transactionGraph(null);
	}

	public static void transactionGraph(TraceabilityList tl) throws Exception {

		try {

			NodeList nodeList = new NodeList();
			NodeList recMgt = new NodeList();
			EdgesList edgesList = new EdgesList();

			int currentSize = nodeList.size();
			//////////////////////////////////////
			{

				Node node = new Node();
				node.setId(currentSize);
				node.setLabel("0");
				node.setGroup(1);
				/* } */
				recMgt.add(node);
				nodeList.add(node);
				String data = tl.getData();
				String[] evidence = data.split(";");

				for (int j = 0; j < evidence.length; j++) {
					evidence[j] = evidence[j].trim();
					if (evidence[j] != null && evidence[j].length() != 0) {
						Node nodeEv = new Node();
						currentSize += 1;
						nodeEv.setId(currentSize);
						nodeEv.setLabel(evidence[j]);
						nodeEv.setGroup(2);
						nodeList.add(nodeEv);
						Edge edge = new Edge();
						edge.setFrom(currentSize);
						edge.setTo(node.getId());
						edgesList.add(edge);
					}
				}
				Node nodeDesc = new Node();
				currentSize += 1;
				nodeDesc.setId(currentSize);
				nodeDesc.setLabel(tl.getDescription());
				nodeDesc.setGroup(2);
				nodeList.add(nodeDesc);
				Edge edgeDesc = new Edge();
				edgeDesc.setFrom(currentSize);
				edgeDesc.setTo(node.getId());
				edgesList.add(edgeDesc);
			}
			//////////////////////////////////////

			ArrayList<Transaction> arrayList = tl.getTransactions();

			TransactionList rl = new TransactionList();

			for (Transaction t : arrayList) {
				rl.add(t);
			}

			for (int i = 0; i < rl.size(); i++) {
				currentSize = nodeList.size();
				Transaction r = rl.get(i);
				Node node = new Node();
				currentSize += 1;
				node.setId(currentSize);
				node.setLabel("" + (i + 1));
				/*
				 * if (cred.getAddress().equalsIgnoreCase(r.getReceiver())) { node.setGroup(3);
				 * } else {
				 */
				node.setGroup(1);
				/* } */
				recMgt.add(node);
				nodeList.add(node);
				String data = r.getTransactionData();
				String[] evidence = data.split(";");

				for (int j = 0; j < evidence.length; j++) {
					// System.out.println("'"+evidence[j] +"'"+evidence[j].length());
					evidence[j] = evidence[j].trim();
					if (evidence[j] != null && evidence[j].length() != 0) {
						Node nodeEv = new Node();
						currentSize += 1;
						nodeEv.setId(currentSize);
						nodeEv.setLabel(evidence[j]);
						nodeEv.setGroup(2);
						nodeList.add(nodeEv);
						Edge edge = new Edge();
						edge.setFrom(currentSize);
						edge.setTo(node.getId());
						edgesList.add(edge);
					}
				}
			}
			for (int i = 1; i < recMgt.size(); i++) {
				Edge edge = new Edge();
				edge.setFrom(recMgt.get(i - 1).getId());
				edge.setTo(recMgt.get(i).getId());
				edgesList.add(edge);
			}

			TraceGraph traceGraph = new TraceGraph();
			traceGraph.setNodes(nodeList);
			traceGraph.setEdges(edgesList);
			System.out.println(new ObjectMapper().writeValueAsString(traceGraph));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Problem when creating graph data . \n[" + e.getMessage() + "] !");

		}

	}

	public static void parentGraph(ArrayList<BaseTransaction> parents) throws Exception {

		try {

			NodeList nodeList = new NodeList();
			NodeList recMgt = new NodeList();
			EdgesList edgesList = new EdgesList();
			HashMap<String, Integer> id2intMapper = new HashMap<String, Integer>();

			//////////////////////////////////////

			int currentSize = nodeList.size();
			for (BaseTransaction bt : parents) {
				if (!id2intMapper.containsKey(bt.getId())) {
					id2intMapper.put(bt.getId(), currentSize);
					currentSize += 1;
				}
				Node node = new Node();
				node.setId(id2intMapper.get(bt.getId()));
				node.setLabel(bt.getId());
				node.setGroup(1);
				recMgt.add(node);
				nodeList.add(node);
				ArrayList<String> parentIds = bt.getParents();

				for (String paentId : parentIds) {
					if (paentId != null && paentId.length() != 0) {
						Node nodeEv = new Node();
						if (!id2intMapper.containsKey(paentId)) {
							id2intMapper.put(paentId, currentSize);
							currentSize += 1;
						}
						nodeEv.setId(id2intMapper.get(paentId));
						nodeEv.setLabel(paentId);
						nodeEv.setGroup(2);
						nodeList.add(nodeEv);
						Edge edge = new Edge();
						edge.setFrom(id2intMapper.get(paentId));
						edge.setTo(node.getId());
						edgesList.add(edge);
					}
				}
				Node nodeDesc = new Node();
				if (!id2intMapper.containsKey(bt.getId()+"desc")) {
					id2intMapper.put(bt.getId()+"desc", currentSize);
					currentSize += 1;
				}
				nodeDesc.setId(id2intMapper.get(bt.getId()+"desc"));
				nodeDesc.setLabel(bt.getDescription());
				nodeDesc.setGroup(2);
				nodeList.add(nodeDesc);
				Edge edgeDesc = new Edge();
				edgeDesc.setFrom(id2intMapper.get(bt.getId()+"desc"));
				edgeDesc.setTo(node.getId());
				edgesList.add(edgeDesc);
			}

			//////////////////////////////////////
			for (int i = 1; i < recMgt.size(); i++) {
				Edge edge = new Edge();
				edge.setFrom(recMgt.get(i - 1).getId());
				edge.setTo(recMgt.get(i).getId());
				edgesList.add(edge);
			}

			TraceGraph traceGraph = new TraceGraph();
			traceGraph.setNodes(nodeList);
			traceGraph.setEdges(edgesList);
			System.out.println(new ObjectMapper().writeValueAsString(traceGraph));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Problem when creating graph data . \n[" + e.getMessage() + "] !");

		}

	}

	/*
	 * { SimpleGraph<String, DefaultEdge> g = new SimpleGraph<String,
	 * DefaultEdge>(DefaultEdge.class);
	 * 
	 * String id1 = "id2:id3:id4:id5"; String id2 = "id3:id4:id5"; String id3 =
	 * "id3:id4"; String id4 = "id5"; Hashtable<String, String> data = new
	 * Hashtable<String, String>(); data.put("id1", id1); data.put("id2", id2);
	 * data.put("id3", id3); data.put("id4", id4);
	 * 
	 * String v1 = "v1"; String v2 = "v2"; String v3 = "v3"; String v4 = "v4";
	 * 
	 * // add the vertices g.addVertex(v1); g.addVertex(v2); g.addVertex(v3);
	 * g.addVertex(v4);
	 * 
	 * // add edges to create a circuit g.addEdge(v1, v2); g.addEdge(v2, v3);
	 * g.addEdge(v3, v4); g.addEdge(v4, v1);
	 * 
	 * System.out.println(g.toString()); }
	 */

}
