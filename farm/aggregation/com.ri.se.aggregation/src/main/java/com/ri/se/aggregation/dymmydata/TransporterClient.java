package com.ri.se.aggregation.dymmydata;

import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;

public class TransporterClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ip = "http://localhost";
		downloader(ip, 9031, "/v1/transferedanimal/list/totransfer");
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
