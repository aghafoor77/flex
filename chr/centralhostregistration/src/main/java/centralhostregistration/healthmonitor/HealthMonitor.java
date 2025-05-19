package centralhostregistration.healthmonitor;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import centralhostregistration.persistance.CentralHostRegistration;
import centralhostregistration.persistance.CentralHostRegistrationService;

public class HealthMonitor implements Runnable {

	private CentralHostRegistrationService centralhostregistrationService;

	public HealthMonitor(CentralHostRegistrationService centralhostregistrationService) {
		this.centralhostregistrationService = centralhostregistrationService;
	}

	@Override
	public void run() {
		System.out.println(" ********************* Started heartbeat checker ********************* ");
		while (true) {
			List<CentralHostRegistration> allHosts = centralhostregistrationService.list();	
			for (CentralHostRegistration chr : allHosts) {
				if (chr.getType().equals("host")) {
					String url = "http://" + chr.getAddress() + ":" + chr.getPort() + chr.getHealthCheck();
					//System.err.println("========================================");
					//System.err.println("Status : "+chr.getStatus());
					String status = getUpdate(url);
					if (!chr.getStatus().equals(status)) {
						chr.setStatus(status);
						centralhostregistrationService.put(chr);
					}
					if (status.equals("0")) {
						int hb = chr.getHb();
						//System.out.println(hb);
						//hb = hb + 1;
						//centralhostregistrationService.putHb(chr.getName(), hb);						
					}
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}

	}

	public String getUpdate(String url) {
		try {
			HttpResponse response;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getConnection = new HttpGet(url);
			response = httpClient.execute(getConnection);
			if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 400) {
				return "1";
			}
		} catch (Exception e) {
			//System.err.println("Communication Error: "+e.getMessage());
		}
		return "0";
	}
}
