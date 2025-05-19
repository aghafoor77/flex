package com.ri.se.aggregation.dymmydata;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.crypto.tink.subtle.Base64;
import com.ri.se.aggregation.Representation;
import com.ri.se.aggregation.RestClient;
import com.ri.se.aggregation.acctmanegement.AccountHandling;
import com.ri.se.commonentities.DTOUsers;
import com.ri.se.commonentities.transporter.DTOCarrier;
import com.ri.se.commonentities.transporter.DTOTransportedCattle;
import com.ri.se.commonentities.transporter.DTOTransporter;

public class TransportDummyData {

	public static String ip = "http://localhost";

	public static void main(String[] args) throws Exception {


		int port = 9000;
		
		String resource = "/tapi/idms/v1/auth";
		DTOUsers userDTO = new AccountHandling().authenticate(ip, port, resource, "basic "+Base64.encode("transporter@gmail.com:abdul1234".getBytes()));
		if(Objects.isNull(userDTO)) {
					
		} else {
			System.out.println(userDTO.getVeid());
		}		
		new TransportDummyData().submitTransporterData(userDTO.getVeid());
	}

	public void submitTransporterData(String employeeID) throws Exception {
		String TID = "";
		{
			DTOTransporter dtoTransporter = new DTOTransporter();
			dtoTransporter.setAnimalPeryear("200");
			dtoTransporter.setIsOrganization(false);
			dtoTransporter.setNotes("to transfer animals from farm to slaughterhouse");
			dtoTransporter.setOwnerId(employeeID);
			dtoTransporter.setTID("TID-" + System.currentTimeMillis());

			DTOTransporter dtoTransporter1 = null;

			int port = 9031;
			String resource = "/v1/transporter";
			String str = commit(ip, port, resource, dtoTransporter);
			if (!Objects.isNull(str)) {
				dtoTransporter1 = new ObjectMapper().readValue(str, DTOTransporter.class);
				TID = dtoTransporter1.getTID();
			}
		}
		{
			DTOCarrier dtoCarrier = new DTOCarrier();
			dtoCarrier.setCarrierNumber("GZM886");
			dtoCarrier.setCID("CID" + System.currentTimeMillis());
			dtoCarrier.setLongDistance(true);
			dtoCarrier.setNotes("This is for log distance and can be used max -10- animals !");
			dtoCarrier.setSpecies("Cows;Bull;");
			dtoCarrier.setTID(TID);
			dtoCarrier.setTransportType("stora lastbil");
			DTOCarrier dtoCarrier1 = null;

			int port = 9031;
			String resource = "/v1/carrier";
			String str = commit(ip, port, resource, dtoCarrier);
			if (!Objects.isNull(str)) {
				dtoCarrier1 = new ObjectMapper().readValue(str, DTOCarrier.class);

			}
		}
		{
			DTOCarrier dtoCarrier = new DTOCarrier();
			dtoCarrier.setCarrierNumber("GZM887");
			dtoCarrier.setCID("CID" + System.currentTimeMillis()+2);
			dtoCarrier.setLongDistance(true);
			dtoCarrier.setNotes("This is for log distance and can be used max -15- animals !");
			dtoCarrier.setSpecies("Cows;Bull;");
			dtoCarrier.setTID(TID);
			dtoCarrier.setTransportType("stora lastbil");
			DTOCarrier dtoCarrier1 = null;
			int port = 9031;
			String resource = "/v1/carrier";
			String str = commit(ip, port, resource, dtoCarrier);
			if (!Objects.isNull(str)) {
				dtoCarrier1 = new ObjectMapper().readValue(str, DTOCarrier.class);

			}
		}
		String TCID ="";
		{
			DTOCarrier dtoCarrier = new DTOCarrier();
			dtoCarrier.setCarrierNumber("GZM888");
			dtoCarrier.setCID("CID" + System.currentTimeMillis()+3);
			dtoCarrier.setLongDistance(true);
			dtoCarrier.setNotes("This is for log distance and can be used max -15- animals !");
			dtoCarrier.setSpecies("Cows;Bull");
			dtoCarrier.setTID(TID);
			dtoCarrier.setTransportType("stora lastbil");
			DTOCarrier dtoCarrier1 = null;

			int port = 9031;
			String resource = "/v1/carrier";
			String str = commit(ip, port, resource, dtoCarrier);
			if (!Objects.isNull(str)) {
				dtoCarrier1 = new ObjectMapper().readValue(str, DTOCarrier.class);
				TCID = dtoCarrier1 .getCID();
			}
		}
		//
		/*{
			DTOTransportedCattle dtoTransportedCattle = new DTOTransportedCattle();
			dtoTransportedCattle.setAnimalID("AR-1736988831481");
			dtoTransportedCattle.setCarriernumber("GZM886");
			dtoTransportedCattle.setDepartDate("2024-11-02");
			dtoTransportedCattle.setDestination("This is destination !");
			dtoTransportedCattle.setEnteranceRecord("1235420");
			dtoTransportedCattle.setGHEID("GHEID-83947598347598");
			dtoTransportedCattle.setNotes("Moving for slaughterhouse");
			dtoTransportedCattle.setOtherData("None");
			dtoTransportedCattle.setSource("Uppsala");
			dtoTransportedCattle.setTCID(TCID);
			dtoTransportedCattle.setTemperature(22);
			dtoTransportedCattle.setTransportType("lastbil");
			
		
			DTOTransportedCattle dtoTransportedCattle1 = null;

			int port = 9031;
			String resource = "/v1/transportedcattle";
			String str = commit(ip, port, resource, dtoTransportedCattle);
			if (!Objects.isNull(str)) {
				dtoTransportedCattle1 = new ObjectMapper().readValue(str, DTOTransportedCattle.class);

			}
		}*/
	}

	public static String commit(String ip, int port, String resource, Object data) {
		try {
			System.out.println(data);
			RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
			Representation rep = rc.post("/application" + resource, data, null);
			System.out.println(rep.getCode());
			System.out.println(rep.getBody().toString());
			Scanner scanner = new Scanner(System.in);

			// Message to the user
			System.out.println("Press Enter to continue...");

			// Wait for the user to press Enter
			// scanner.nextLine();
			return rep.getBody().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);

		// Message to the user
		System.out.println("Press Enter to continue...");

		// Wait for the user to press Enter
		// scanner.nextLine();
		return null;
	}

}
