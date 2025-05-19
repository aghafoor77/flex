package org.ri.se.ipfsj.lsc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.ipfs.api.IPFS;
import io.ipfs.api.IPFS.PinType;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class AttachedResourceHandler implements IAttachedResourceHandler {

	private IPFS ipfs;

	
	public AttachedResourceHandler(String ipfsUrl) throws IPFSConnectionException {
		try {
		this.ipfs = new IPFS(ipfsUrl);		
		} catch(Exception exp){
			throw new IPFSConnectionException(exp);
			
		}
	}
	public AttachedResourceHandler(IPFS ipfs) {
		this.ipfs = ipfs;
	}

	@Override
	public DataResourceProps upload(DataResourceProps file) throws Exception {
		File tempFile = new File(file.getPath());
		if (!tempFile.exists()) {
			throw new ResourceNotFoundException(
					"Reosurce not found  at location \n'" + tempFile.getAbsolutePath() + "' !");
		}
		try {
			NamedStreamable.FileWrapper namedFile = new NamedStreamable.FileWrapper(tempFile);
			List<MerkleNode> response = ipfs.add(namedFile);
			if (Objects.isNull(response) || response.size() == 0) {
				throw new Exception("Problems when uploading a resource !");
			}
			System.out.println(response.get(0).hash.toBase58());
			file.setIpfsCID(response.get(0).hash.toBase58());
			return file;
		} catch (IOException ex) {
			throw new Exception("Error whilst communicating with the IPFS node", ex);
		}
	}

	@Override
	public DataResourceProps[] upload(DataResourceProps[] files) throws Exception {
		for (int i = 0; i < files.length; i++) {
			DataResourceProps file = files[i];
			files[i] = upload(file);
		}
		return files;
	}

	public byte[] get(String hash) throws Exception {
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			List<MerkleNode> list = ipfs.ls(multihash);
			if (list.size() > 0) {
				throw new DirectoryException("Contents not found, destination is a directory !");
			}
			byte[] content = ipfs.cat(multihash);
			if (Objects.isNull(content)) {
				throw new NullPointerException("Destination resource is empty !");
			}
			return content;
		} catch (IOException ex) {
			throw ex;
		}
	}

	//-------------------------------------------------------------------
	public void get(String hash, FileOutputStream out) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bout.write(get(hash));
		bout.writeTo(out);
		bout.flush();
		bout.close();
	}

	/*
	 * public void walk(String hash) {
	 * 
	 * if(list.size() == 0 ) {
	 * 
	 * } else {
	 * 
	 * } for (MerkleNode merkleNode : list) { System.out.println(merkleNode.hash);
	 * System.out.println(multihash);
	 * 
	 * 
	 * 
	 * 
	 * if (f.isDirectory()) { walk(f.getAbsolutePath()); System.out.println("Dir:" +
	 * f.getAbsoluteFile()); } else { System.out.println("File:" +
	 * f.getAbsoluteFile()); }
	 * 
	 * } } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	public void walk(String hash) {
	
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			List<MerkleNode> list = ipfs.ls(multihash);
			if (list.size() == 0) {
				System.out.println("File: " + hash);
				return;
			}
			System.out.println("Folder " + hash);
			for (MerkleNode merkleNode : list) {
				List<MerkleNode> subList = ipfs.ls(merkleNode.hash);
				if (subList.size() >= 0) {
					walk(merkleNode.hash.toBase58());
				} else {
					
					System.out.println("File: " + merkleNode.hash.toBase58());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * 
		 * 
		 * File root = new File(path); File[] list = root.listFiles();
		 * 
		 * if (list == null) return;
		 * 
		 * for (File f : list) { if (f.isDirectory()) { walk(f.getAbsolutePath());
		 * System.out.println("Dir:" + f.getAbsoluteFile()); } else {
		 * System.out.println("File:" + f.getAbsoluteFile()); } }
		 */ }

	public byte[] get() {

		try {
			Map<Multihash, Object> list = ipfs.pin.ls(PinType.all);
			Iterator<Multihash> hashes = list.keySet().iterator();
			while (hashes.hasNext()) {
				Multihash mh = hashes.next();
				Object obj = list.get(mh);
				LinkedHashMap linkedHashMap = (LinkedHashMap) obj;
			}

			list.forEach((hash, type) -> {
				LinkedHashMap linkedHashMap = (LinkedHashMap) type;
				System.out.println(linkedHashMap.get("Type"));
				if (!linkedHashMap.get("Type").toString().equalsIgnoreCase("recursive")) {
					System.out.println("Multihash: " + hash + " - type: " + type);
					byte[] content;
					try {
						content = get(hash.toString());

						if (!Objects.isNull(content)) {
							System.out.println("=====================================================");
							System.out.println(new String(content));
							// displayFile(hash.toString(), content);
							// return content;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// ipfs.pin.ls(PinType.all);
					System.out.println("=====================================================");
					System.out.println(hash);

					// displayFile(hash.toString(), content);

				}

			});
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
		return null;
	}

	public void readFile() {
		// QmQxfp8KvRMMbVUXA2fsGxcs3hVR2T7PDHtoMWAPnekpUo
	}

	public static void main(String[] args) {
		IPFS ipfs = new IPFS("/ip4/172.17.0.3/tcp/5001");
		//AttachedResourceHandler documentHandler = new AttachedResourceHandler(ipfs);
		//DataResourceProps file = new DataResourceProps();
		//file.setPath("/home/blockchain/Desktop/temp/je.txt");
		try {
			//System.out.println(ipfs.swarm.peers().size());
			try {
				Map<Multihash, Object> list = ipfs.pin.ls(PinType.all);
				Iterator<Multihash> hashes = list.keySet().iterator();
				list.forEach((hash, type) -> {
					LinkedHashMap linkedHashMap = (LinkedHashMap) type;
					System.out.println(linkedHashMap.get("Type"));
					if (!linkedHashMap.get("Type").toString().equalsIgnoreCase("recursive")) {
						System.out.println("Multihash: " + hash + " - type: " + type);
						byte[] content = readFile(ipfs, hash.toString());
						if (!Objects.isNull(content)) {
							System.out.println("=====================================================");
							displayFile(hash.toString(), content);
						}
					}

				});
			} catch (IOException ex) {
				throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static byte[] readFile(IPFS ipfs, String hash) {
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			byte[] content = ipfs.cat(multihash);
			return content;
		} catch (IOException ex) {
			// throw new RuntimeException("Error whilst communicating with the
			// IPFS node", ex);
			return null;
		}
	}

	public static void displayFile(String hash, byte[] content) {
		// System.out.println(new String(content));
		System.out.println("Content of " + hash + ": " + new String(content));
	}

}
