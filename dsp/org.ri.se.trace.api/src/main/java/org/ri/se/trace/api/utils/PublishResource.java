package org.ri.se.trace.api.utils;

public class PublishResource {

	
	public boolean publish(String did,	String role,String publickey,String company) throws Exception{
		SCResource scResource = new SCResource();
		scResource.setDid(did);
		scResource .setCompany(company);
		scResource .setPublickey(publickey);
		scResource .setRole(role);
		String basedURL = "http://localhost:9030";
		RestClient rc = RestClient.builder().baseUrl(basedURL).build();
		String resource = "/v1/scresource";
		Representation rep = rc.post("/application" + resource, scResource, null);
		
		return true;
	}
	
}
