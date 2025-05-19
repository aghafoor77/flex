package com.ri.se.transporter.uploader;

public class SubmitTrasactionTransporter {
	
	public String submit(String ip, int port, String resource, Object data) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			Representation rep = rc.post("/application" + resource, data, null);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			return rep.getBody().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
