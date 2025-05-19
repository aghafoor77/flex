package com.rise.vdr.api.main;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Objects;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rise.vdr.api.utils.BasicAuthenticator;

import io.dropwizard.auth.AuthFilter;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class VDRVerificationFilter extends AuthFilter {

	final static Logger logger = LoggerFactory.getLogger(VDRVerificationFilter.class);

	public VDRVerificationFilter() {

	}

	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			String value = requestContext.getHeaderString("Authorization");
			if (Objects.isNull(value)) {
				throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
						.entity("You are not authorized to access !").build());
			}
			value = value.trim();
			String temp = value.substring(0, value.indexOf(" "));
			temp = temp.trim().toLowerCase();

			if (temp.equalsIgnoreCase("basic")) {
				Hashtable<String, String> output = new BasicAuthenticator().authenticate(value);
				requestContext.getHeaders().add("owner", output.get(BasicAuthenticator.OWNER));
				requestContext.getHeaders().add("pass", output.get(BasicAuthenticator.PASSWORD));

			} else {
				throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
						.entity("Does not support '" + temp + "' authentication protocol !").build());
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build());
		}
	}
}