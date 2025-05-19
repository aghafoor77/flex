package org.ri.se.verifiablecredentials.entities;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VCDocument extends HashMap<String, Object> {
	private URI did;
	private List<String> context = new ArrayList<String>();
	private List<VCService> service;
	private List<VCVerificationMethod> verificationMethod;

	public URI getDid() {
		return did;
	}

	public void setDid(URI did) {
		this.did = did;
	}

	public List<VCService> getService() {
		return service;
	}

	public void setService(List<VCService> service) {
		this.service = service;
	}

	public List<VCVerificationMethod> getVerificationMethod() {
		return verificationMethod;
	}

	public void setVerificationMethod(List<VCVerificationMethod> verificationMethod) {
		this.verificationMethod = verificationMethod;
	}

	public List<String> getContext() {
		return context;
	}

	public void setContext(List<String> context) {
		this.context = context;
	}

	public void addContext(String context_) {
		if (Objects.isNull(context)) {
			context = new ArrayList<String>();
		}
		context.add(context_);
	}

	public void addVCService(VCService vcService) {
		if (Objects.isNull(service)) {
			service = new ArrayList<VCService>();
		}
		service.add(vcService);
	}

	public void addVCVCVerificationMethod(VCVerificationMethod vcVerificationMethod) {
		if (Objects.isNull(verificationMethod)) {
			verificationMethod = new ArrayList<VCVerificationMethod>();
		}
		verificationMethod.add(vcVerificationMethod);
	}

	public HashMap<String, Object> toHashMap() {
		if (!Objects.isNull(service)) {
			this.put("service", service);
		}
		if (!Objects.isNull(did)) {
			this.put("did", did);
		}
		if (!Objects.isNull(verificationMethod)) {
			this.put("verificationMethod", verificationMethod);
		}
		if (!Objects.isNull(context)) {
			this.put("@context", context);
		}

		return this;
	}

	public String serialize() throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(toHashMap());
	}
}
