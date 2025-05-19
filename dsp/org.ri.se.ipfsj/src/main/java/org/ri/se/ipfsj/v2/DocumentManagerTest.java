package org.ri.se.ipfsj.v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.ipfs.api.IPFS;

public class DocumentManagerTest {

	public static void main(String[] args) throws FileNotFoundException, Exception {
		test() ;
	}

	public static void test() throws FileNotFoundException, Exception {
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		IPFSFileDescriptor descriptor = documentManager.upload("/home/chaincode/Desktop/dsp/formas-main/dsp/org.ri.se.ipfsj/ab.txt");
		System.out.println(descriptor.getHashBase58());
		System.out.println(new String(documentManager.download(descriptor.getHashBase58())));
		System.out.println(documentManager.download(descriptor.getHashBase58(), new FileOutputStream("down.txt")));
		/*List<IPFSFileDescriptor> descriptors = documentManager.uploadDirectory("/home/ag/Desktop/ipfsupload/");
		String directory = null;
		for (IPFSFileDescriptor ipfsFileDescriptor : descriptors) {
			System.out.print("Type: " + ipfsFileDescriptor.getType().value());
			System.out.println(" , Hash: " + ipfsFileDescriptor.getHashBase58());
			if(ipfsFileDescriptor.getType()== IPFSFileDescriptor.FileType.DIRECTORY) {
				directory = ipfsFileDescriptor.getHashBase58();
			}
		}
		List<String> files1 = new ArrayList<String>();
		documentManager.list(directory,  files1);*/
		
	}

}
