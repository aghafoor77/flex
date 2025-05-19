package org.ri.se.trace.api.register;

import java.net.URI;

import org.acreo.common.utils.RestClient;
import org.acreo.security.utils.SGen;
import org.apache.http.HttpRequest;
import org.ri.se.trace.api.utils.Farmer;

public class DeleteMe {

	public static void main(String[] args) throws Exception {
		RestClient rc = RestClient.builder().baseUrl("http://10.112.0.39:9020").build();
		Farmer farmer = new Farmer();
		farmer.setContact("contact");
		farmer.setCountry("country");
		farmer.setCounty("county");
		farmer.setEmail("email@gmail.com");
		farmer.setFarmId("farmid");
		farmer.setMunicipality("municipality");
		farmer.setName("name");
		farmer.setPostcode("postcode");
		farmer.setResourceId("resourceId");
		farmer.setRole("role");
		farmer.setStreet("street");
		
		farmer.setResourceId(new SGen().nextString(12));
		
		rc.post("/application/v1/farmer", farmer, null);
		
		

	}

}
