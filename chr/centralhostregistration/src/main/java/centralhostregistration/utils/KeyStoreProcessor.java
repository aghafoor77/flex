package centralhostregistration.utils;

import java.io.File;

public class KeyStoreProcessor implements Runnable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private String storeFilePath;

	public KeyStoreProcessor(String storeFilePath) {
		this.storeFilePath = storeFilePath;
	}

	public void run() {
		while (true) {
			try {
				
				if (new File(storeFilePath).exists()) {
					System.out.println("File exists  . . .");
					new ManageOthers().manageKeys(storeFilePath);
					new File(storeFilePath).delete();
					return;
				}
				System.out.println("File not exist  . . .");
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
