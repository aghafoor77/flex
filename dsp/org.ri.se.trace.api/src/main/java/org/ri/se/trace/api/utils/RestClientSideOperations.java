package org.ri.se.trace.api.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ri.se.trace.api.main.TraceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClientSideOperations {

	private static final Logger logger_ = LoggerFactory.getLogger(RestClientSideOperations.class);

	public ECAccount getAcct(String url) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.setHeader("Content-type", "application/json");
			logger_.info("===> Fetching ECAccount Data " + url);

			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				ECAccount ecAccount = new ObjectMapper().readValue(responseString.getBytes(), ECAccount.class);
				logger_.debug("===> ECAccount Data " + ecAccount.getKeyNo());
				return ecAccount;
			} else {
				logger_.error("===> HTTP GET ECAccount : Response status " + response.getStatusLine().getStatusCode());
			}
			return null;
		} catch (Exception exp) {
			logger_.error("===> Problems when creating connection to " + url + " !");
			logger_.error("===> Exception message : " + exp.getMessage());
			return null;
		}

	}

	public boolean consumed(String url, String keyNo) {
		try {
			HttpClient client = new DefaultHttpClient();
			logger_.info("===> HTTP POST :"+url + "/" + keyNo);
			HttpPost post = new HttpPost(url + "/" + keyNo);
			post.setHeader("Content-type", "application/json");

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				logger_.error("===> Problems when send post request to '" + url + "' for updating key no" + keyNo + " !");
				return false;
			}
			logger_.info("===> Successfully key no "+keyNo+" consumed !");
		} catch (Exception exp) {
			logger_.error("===> Problems when creating connection to " + url + " !");
			logger_.error("===> Exception message : " + exp.getMessage());
		}
		return true;

	}

	public static void main(String[] args) throws IOException {
		new RestClientSideOperations()
				.getAcct("http://localhost:9090/application/v1/centralhostregistration/avail/key");
	}

	public DTOCentralHostRegistration getIP(String url, String hostname) {
		try {
			logger_.info("===> Fetching IP address of host " + hostname);
			logger_.info("===> HTTP GET :" + url + "/" + hostname);
			logger_.debug("===> HTTP GET :" + url + "/" + hostname);
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url + "/" + hostname);
			get.setHeader("Content-type", "application/json");
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {

				HttpEntity entity = response.getEntity();
				String responseString = EntityUtils.toString(entity, "UTF-8");
				DTOCentralHostRegistration dtoCentralHostRegistration = new ObjectMapper()
						.readValue(responseString.getBytes(), DTOCentralHostRegistration.class);
				logger_.debug("===> HTTP GET RESPONSE :" + dtoCentralHostRegistration.getAddress());
				return dtoCentralHostRegistration;
			} else {
				logger_.error("===> HTTP GET : Response status " + response.getStatusLine().getStatusCode());
			}
			return null;
		} catch (Exception exp) {
			exp.printStackTrace();
			logger_.error("===> Problems when creating connection to " + url + " !");
			logger_.error("===> Exception message : " + exp.getMessage());
			return null;
		}

	}

}
