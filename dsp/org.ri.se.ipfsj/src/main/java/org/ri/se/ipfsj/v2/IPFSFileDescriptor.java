package org.ri.se.ipfsj.v2;

public class IPFSFileDescriptor {

	enum FileType {
		FILE("file"), DIRECTORY("directory");
		private final String fileType;
		private FileType(String fileType) {
			this.fileType = fileType;
		}
		public String getFileType() {
			return fileType;
		}
		public String value() {
			return fileType;
		}
	}

	private String name;
	private String ipfsname;
	private String hashBase58;
	private FileType type;

	public IPFSFileDescriptor() {
		super();
	}

	public IPFSFileDescriptor(String name, String ipfsname, String hashBase58, FileType type) {
		super();
		this.name = name;
		this.ipfsname = ipfsname;
		this.hashBase58 = hashBase58;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHashBase58() {
		return hashBase58;
	}

	public void setHashBase58(String hashBase58) {
		this.hashBase58 = hashBase58;
	}

	public String getIpfsname() {
		return ipfsname;
	}

	public void setIpfsname(String ipfsname) {
		this.ipfsname = ipfsname;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}
	
}
