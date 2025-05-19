package org.ri.se.ipfsj.v2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.ri.se.ipfsj.v2.IPFSFileDescriptor.FileType;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class DocumentManager {

	public static void main(String[] args) throws IOException {

		// List<File> files = new ArrayList<File>();
		// listf("/home/ag/Desktop/", files);
		/// ip4/127.0.0.1/tcp/5001
		// IPFS ipfs = new IPFS("192.168.10.101", 5001);
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		documentManager.getPeer();

		//
		try {
			System.out.println(new File("ab.txt").getAbsolutePath());
			documentManager.download("QmVDYy5MMDuco4hDxK6YvrWvrhV8YvXEkPxRLrXWBsn5X6");
		} catch (Exception e) {
			// TODO Auto-generated
			e.printStackTrace();
		}
		
		/*
		 * List<String> files1 = new ArrayList<String>(); String dd =
		 * "QmdK7uofoMyTUHXrQH6qeEgJkmjwwzH5Sgq3Hzk5VNZbJG"; documentManager.list(dd,
		 * files1); System.out.println(files1.size()); // List<String> files2 = new
		 * ArrayList<String>();
		 * documentManager.list("QmPiDu8zeWAt5eTFXaJfDhATWkG3JCoNa6N4JGwtphoHor",
		 * files2); System.out.println(files2.size()); try { System.out.println(new
		 * File("ab.txt").getAbsolutePath()); documentManager.download(files1.get(0),
		 * new FileOutputStream("ab.txt")); } catch (Exception e) { // TODO
		 * Auto-generated e.printStackTrace(); }
		 */
		// System.out.println(isDirectory("QmPiDu8zeWAt5eTFXaJfDhATWkG3JCoNa6N4JGwtphoHor",
		// ipfs));
		// walk("QmPiDu8zeWAt5eTFXaJfDhATWkG3JCoNa6N4JGwtphoHor", ipfs);
		// get(ipfs);
		// IPFSFileDescriptor ipfsFileDescriptor =
		// upload("/home/ag/Desktop/ipfsupload/abc.txt", ipfs);
		// System.out.println(ipfsFileDescriptor.getName());
		// System.out.println(ipfsFileDescriptor.getHashBase58());

		// walk(dd, ipfs);
		/*
		 * byte[] content = readFile(ipfs, dd); if (!Objects.isNull(content)) {
		 * System.out.println("=====================================================");
		 * displayFile(dd.toString(), content); }
		 */
		// getPeer(ipfs);
		//
// Directory upload 

		/*
		 * List<IPFSFileDescriptor> descriptors =
		 * uploadDirectory("/home/ag/Desktop/ipfsupload/", ipfs); for
		 * (IPFSFileDescriptor IPFSFileDescriptor : descriptors) { System.out.println(
		 * Objects.isNull(IPFSFileDescriptor.getIpfsname()) ? "null" :
		 * IPFSFileDescriptor.getIpfsname()); System.out.println(
		 * Objects.isNull(IPFSFileDescriptor.getIpfsname()) ? "null" :
		 * IPFSFileDescriptor.getHashBase58()); System.out
		 * .println(Objects.isNull(IPFSFileDescriptor.getIpfsname()) ? "null" :
		 * IPFSFileDescriptor.getName()); System.out
		 * .println(Objects.isNull(IPFSFileDescriptor.getType()) ? "null" :
		 * IPFSFileDescriptor.getType().value()); }
		 */

		// QmYVTt21XTwsBTM7oUA5VH3RUC4wNeQhbvM7HMsLdZWnS8
		// System.out.println();ipfs.stats);

		// uploadDirectory(ipfs);

		// readFile(ipfs, "QmYG9hkb5KJdsVAPFMwUoFcUGA69jsKVYCBAsGVbNFkytp");
		/*
		 * try { Map<Multihash, Object> list = ipfs.pin.ls(PinType.all);
		 * Iterator<Multihash> hashes = list.keySet().iterator(); list.forEach((hash,
		 * type) -> { LinkedHashMap linkedHashMap = (LinkedHashMap) type;
		 * System.out.println(linkedHashMap.get("Type")); if
		 * (!linkedHashMap.get("Type").toString().equalsIgnoreCase("recursive")) {
		 * System.out.println("Multihash: " + hash + " - type: " + type); byte[] content
		 * = readFile(ipfs, hash.toString()); if (!Objects.isNull(content)) {
		 * System.out.println("=====================================================");
		 * displayFile(hash.toString(), content); } }
		 * 
		 * }); } catch (IOException ex) { throw new
		 * RuntimeException("Error whilst communicating with the IPFS node", ex); }
		 */
	}

	private IPFS ipfs = null;

	public DocumentManager(IPFS ipfs) {
		this.ipfs = ipfs;
	}

	public DocumentManager(String ipfsUrl) {
		ipfs = new IPFS(ipfsUrl);
	}

	public void list(String directoryName, List<String> outFiles) {
		File directory = new File(directoryName);
		if (!isDirectory(directoryName)) {
			outFiles.add(directoryName);
			return;
		}
		File[] fList = directory.listFiles();
		Multihash multihash = Multihash.fromBase58(directoryName);
		List<MerkleNode> list = null;
		try {
			list = ipfs.ls(multihash);
			if (fList != null || list.size() != 0)
				for (MerkleNode file : list) {
					if (!isDirectory(file.hash.toBase58())) {
						outFiles.add(file.hash.toBase58());
					} else {
						list(file.hash.toBase58(), outFiles);
					}
				}
		} catch (Exception e) {

		}

	}

	public boolean isDirectory(String hash) {
		Multihash multihash = Multihash.fromBase58(hash);
		List<MerkleNode> list = null;
		try {
			list = ipfs.ls(multihash);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void displayFile(String hash, byte[] content) {
		// System.out.println(new String(content));
		System.out.println("Content of " + hash + ": " + new String(content));
	}

	public IPFSFileDescriptor upload(File file) {
		if (!file.exists()) {
			throw new RuntimeException("File '" + file.getAbsolutePath() + "' does not exist !",
					new FileNotFoundException(""));
		}
		try {
			NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(new FileInputStream(file));
			MerkleNode response = ipfs.add(is).get(0);
			return new IPFSFileDescriptor(file.getName(), response.name.get(), response.hash.toBase58(), FileType.FILE);
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
	}

	public IPFSFileDescriptor[] upload(File[] files) {
		for (File file : files) {
			if (!file.exists()) {
				throw new RuntimeException("File '" + file.getAbsolutePath() + "' does not exist !",
						new FileNotFoundException(""));
			}
		}
		IPFSFileDescriptor[] ipfsFileDescriptors = new IPFSFileDescriptor[files.length];
		int i = 0;
		for (File file : files) {

			ipfsFileDescriptors[i++] = upload(file);

		}
		return ipfsFileDescriptors;

	}

	public IPFSFileDescriptor upload(String filePath) {
		if (!new File(filePath).exists()) {
			throw new RuntimeException("File '" + filePath + "' does not exist !", new FileNotFoundException(""));
		}
		try {
			NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(
					new FileInputStream(filePath));
			MerkleNode response = ipfs.add(is).get(0);

			return new IPFSFileDescriptor(new File(filePath).getName(), response.name.get(), response.hash.toBase58(),
					FileType.FILE);
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
	}

	public IPFSFileDescriptor upload(String name, byte fileData[]) {
		try {
			NamedStreamable.ByteArrayWrapper bytearray = new NamedStreamable.ByteArrayWrapper(fileData);
			MerkleNode response = ipfs.add(bytearray).get(0);
			System.out.println("Hash (base 58): " + response.hash.toBase58());
			return new IPFSFileDescriptor(name, response.name.get(), response.hash.toBase58(), FileType.FILE);
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}

	}

	public List<IPFSFileDescriptor> uploadDirectory(String directory) {
		try {
			if (!new File(directory).exists()) {
				throw new RuntimeException("Directory '" + directory + "' does not exist !",
						new FileNotFoundException(""));
			}
			if (!new File(directory).isDirectory()) {
				throw new RuntimeException("'" + directory + "' is not a directory !");
			}
			File files[] = new File(directory).listFiles();
			if (files.length == 0) {
				throw new RuntimeException("'" + directory + "' directory is empty !");
			}
			List<NamedStreamable> ipfsList = new ArrayList<NamedStreamable>();

			for (File file : files) {
				NamedStreamable.FileWrapper ipfsFile = new NamedStreamable.FileWrapper(file);
				ipfsList.add(ipfsFile);
			}

			NamedStreamable.DirWrapper ipfsDirectory = new NamedStreamable.DirWrapper(directory, ipfsList);
			List<MerkleNode> response = ipfs.add(ipfsDirectory);
			List<IPFSFileDescriptor> ipfsFileDescriptors = new ArrayList<IPFSFileDescriptor>();
			int i = 0;

			for (MerkleNode merkleNode : response) {
				if (!directory.startsWith(File.separator)) {
					directory = File.separator + directory;
				}
				if (directory.endsWith(File.separator)) {
					directory = directory.substring(0, directory.length() - 1);
				}
				String ipfsFileName = File.separator + merkleNode.name.get();
				if (new File(ipfsFileName).isFile() || (ipfsFileName).equalsIgnoreCase(directory)) {
					ipfsFileDescriptors.add(new IPFSFileDescriptor(merkleNode.name.get(), merkleNode.name.get(),
							merkleNode.hash.toBase58(),
							new File(ipfsFileName).isDirectory() ? FileType.DIRECTORY : FileType.FILE));
				}
			}
			return ipfsFileDescriptors;
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
	}

	public byte[] download(String hash) {
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			byte[] content = ipfs.cat(multihash);
			System.out.println(new String(content));
			return content;
		} catch (IOException ex) {
			return null;
		}
	}

	// -------------------------------------------------------------------

	public boolean download(String hash, FileOutputStream out) throws Exception {
		System.err.println("Downloading "+hash+" file from IPFS ");
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] contents = download(hash);
		if (Objects.isNull(contents) || contents.length == 0) {
			throw new Exception("Source file is empty !");
		}
		bout.write(contents);
		bout.writeTo(out);
		bout.flush();
		bout.close();
		System.err.println("File "+hash+" sucessfully downloaded from IPFS ");
		return true;
	}

	public void pinHash(String hash) {
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			ipfs.pin.add(multihash);
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
	}

	public void unpinHash(String hash) {
		try {
			Multihash multihash = Multihash.fromBase58(hash);
			ipfs.pin.rm(multihash);
		} catch (IOException ex) {
			throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
		}
	}

	public void getPeer() throws IOException {
		List<Multihash> peers = ipfs.refs.local();
		peers.forEach(multihash -> System.out.println("Peer ID: " + multihash));
	}
}
