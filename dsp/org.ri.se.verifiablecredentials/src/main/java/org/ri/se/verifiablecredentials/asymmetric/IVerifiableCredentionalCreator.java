package org.ri.se.verifiablecredentials.asymmetric;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.ri.se.verifiablecredentials.entities.ProofAttributes;

public interface IVerifiableCredentionalCreator {

	public String create(URI did, String verificatioKeyURI, HashMap<String, Object> baseJson,
			Map<String, Object> properties, byte[] privatekey, byte[] publickey, ProofAttributes proofAtt)
			throws Exception;
	public boolean verifyOnline(String json, byte[] publickey) throws Exception;
	public boolean verifyOffline(String json) throws Exception;
}
