package analytics.dto.graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphData {
	private List<String> labels = new ArrayList<String>();
	private List<Double> data = new ArrayList<Double>();

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
}
