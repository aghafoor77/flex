package org.ri.se.trace.api.main;

import java.util.Hashtable;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BasicAuthenticator implements IAuthenticator {

	

	public Hashtable<String, String> authenticate(String info) {
		System.out.println(info);
		Hashtable<String, String> output = new Hashtable<String, String>();

		String temp = info.substring(info.indexOf(" "));
		byte ss[] = org.bouncycastle.util.encoders.Base64.decode(temp);
		temp = new String(ss);
		if (temp.contains(":")) {
			String username = temp.substring(0, temp.indexOf(":"));
			String password = temp.substring(temp.indexOf(":") + 1);
			output.put(OWNER, username);
			output.put(PASSWORD, password);
			return output;
		} else {
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED).entity("Bad request !").build());
		}

	}

}
