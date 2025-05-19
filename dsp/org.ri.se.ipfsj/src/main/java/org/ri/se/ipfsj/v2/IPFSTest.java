package org.ri.se.ipfsj.v2;

import io.ipfs.api.IPFS;

public class IPFSTest {

	public static void main(String[] args) {
		IPFS ipfs = new IPFS("/ip4/172.17.0.3/tcp/5001");
		
		try {
			System.out.println(ipfs.swarm);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
