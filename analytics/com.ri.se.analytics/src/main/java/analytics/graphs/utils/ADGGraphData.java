package analytics.graphs.utils;

import java.util.ArrayList;
import java.util.List;

import analytics.dto.graphs.GraphData;

public class ADGGraphData {
	private GraphData graphData = null;

	public void init() {
		List<String> labels = new ArrayList<String>();
		labels.add("Animal-1");
		labels.add("Animal-2");
		labels.add("Animal-3");
		labels.add("Animal-4");
		labels.add("Animal-5");
		labels.add("Animal-6");
		labels.add("Animal-7");
		labels.add("Animal-8");
		labels.add("Animal-9");
		labels.add("Animal-10");
		labels.add("Animal-11");
		labels.add("Animal-12");
		List<Double> data = new ArrayList<Double>();
		data.add(1.0);
		data.add(2.0);
		data.add(3.0);
		data.add(4.0);
		data.add(5.0);
		data.add(6.0);
		data.add(7.0);
		data.add(8.0);
		data.add(9.0);
		data.add(10.0);
		data.add(11.0);
		data.add(12.0);
		graphData = new GraphData ();
		graphData.setData(data);
		graphData.setLabels(labels);
		
	}
	public GraphData getGraphData() {
		init();
		
		return graphData;
	}
}
