package com.ri.se.aggregation.dymmydata;

import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;

public class SlaughterhouseClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ip = "http://localhost";
		downloader(ip, 9050, "/v1/slaughterhouse/download/vdr/35685379632a6c545a67444b3b4739727e5754396448554978443473443b");
	}

	public static String downloader(String ip, int port, String resource) {
		try {
			RestClient rc = RestClient.builder().baseUrl(ip).build();
			Representation rep = rc.get(":" + port + "/application" + resource, null);
			System.out.println(rep.getBody().toString());
			return rep.getBody().toString();
		} catch (Exception exp) {
			System.out.println("==========> No Data <==========" + exp.getMessage());
			return null;
		}
	}
}
