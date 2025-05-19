package com.ri.se.aggregation;

public class Submitter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ip = "http://localhost";
		String animalID = "AR-1736988831481";
		SubmitTransaction submitTransaction = new SubmitTransaction();
		StringList animals = new StringList();
		animals .add(animalID);
		submitTransaction.setAnimals(animals); 
		submitTransaction.setFrom("4433372a5164534c782d382d456b56756c795449744a2a4f783164705257");
		submitTransaction.setTo("6a77646c68597a6361684d3b6d2a7e576b557665504342534e4959377447");
		Submitter.submit(ip, 9030, "/v1/sctransaction/submit", submitTransaction);
	}
	public static String submit(String ip, int port, String resource, Object data) {
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
