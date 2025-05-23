package com.rise.se.slaughterhouse.downloader;



import java.util.HashMap;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {
	
	private boolean showLog = true;
	
	private HttpClient httpClient = null;
	private String baseUrl = null;
	private int timeout = 5;
	private RequestConfig requestConfig = RequestConfig.custom()
			  .setSocketTimeout(timeout * 1000)
			  .setConnectTimeout(timeout * 1000)
			  .setConnectionRequestTimeout(timeout * 1000)
			  .build();
	public RestClient(HttpClient httpClient, String baseUrl) {
		
		this.httpClient = httpClient;
		this.baseUrl = baseUrl;		
	}

	public Representation get(String resourceUrl, HashMap<String, String> headers) throws Exception {
		try {
			if(this.showLog)
				System.out.println("GET ---> "+this.baseUrl + resourceUrl);
			
			HttpGet request = new HttpGet(this.baseUrl + resourceUrl);
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			request.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(request);
			return processResponse(response);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public Representation post(String resourceUrl, Object data, HashMap<String, String> headers)
			throws Exception {
		if(this.showLog)
			System.out.println("POST ---> "+this.baseUrl + resourceUrl);
		try {
			HttpPost request = new HttpPost(this.baseUrl + resourceUrl);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(data);
			request.setEntity(fillStringEntity(json));
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			
			request.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(request);
			return processResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			Representation r = new Representation(-1, e.getMessage());
			throw new Exception(r.toJson());
		}
	}
	
	public Representation postAsItIs(String resourceUrl, String data, HashMap<String, String> headers)
			throws Exception {
		if(this.showLog)
			System.out.println("POST (postAsItIs) ---> "+this.baseUrl + resourceUrl);
		try {
			HttpPost request = new HttpPost(this.baseUrl + resourceUrl);
			request.setEntity(fillStringEntity(data));
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			
			request.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(request);
			return processResponse(response);
		} catch (Exception e) {
			Representation r = new Representation(-1, e.getMessage());
			throw new Exception(r.toJson());
		}
	}
	public Representation put(String resourceUrl, Object data, HashMap<String, String> headers)
			throws Exception {
		if(this.showLog)
			System.out.println("PUT ---> "+this.baseUrl + resourceUrl);
		try {
			HttpPut request = new HttpPut(this.baseUrl + resourceUrl);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(data);
			request.setEntity(fillStringEntity(json));
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			
			request.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(request);
			return processResponse(response);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public Representation delete(String resourceUrl, HashMap<String, String> headers)
			throws Exception {
		if(this.showLog)
			System.out.println("DELETE ---> "+this.baseUrl + resourceUrl);
		try {
			HttpDelete request = new HttpDelete(this.baseUrl + resourceUrl);
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			
			request.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(request);
			return processResponse(response);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private StringEntity fillStringEntity(String data) {
		StringEntity params = new StringEntity(data, "UTF-8");
		params.setContentType("application/json");
		return params;
	}

	private Representation processResponse(HttpResponse response) throws Exception {
		
		try {
			HttpEntity entity = response.getEntity();
			
			String dataRec = EntityUtils.toString(entity);
			
			Representation representation = entity != null
					? new Representation(response.getStatusLine().getStatusCode(), dataRec )
					: new Representation(-1, "Processig response error");			
			return representation;

		} catch (Exception exp) {
			
			Representation r = new Representation(response.getStatusLine().getStatusCode(),
					response.getStatusLine().getReasonPhrase());
			throw new Exception(r.toJson());
		}
	}

	public HashMap<String, String> getDefaultHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("content-type", "application/json");
		headers.put("Accept", "*/*");
		headers.put("Accept-Encoding", "gzip,deflate,sdch");
		headers.put("Accept-Language", "en-US,en;q=0.8");
		return headers;
	}
	
	public static RestClient.Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private String baseUrl;

		protected Builder() {
		}

		public RestClient.Builder baseUrl(String baseUrl) {
			if (null == baseUrl) {
				return this;
			}
			this.baseUrl = baseUrl;
			return this;
		}

		public RestClient build() throws Exception {

			if (this.baseUrl == null) {
				throw new Exception("Base URL cannot be null !");
			}
			HttpClient client = new DefaultHttpClient ();
			return new RestClient(client, this.baseUrl);
		}
	}
	public void setShowLog(boolean showLog ){
		this.showLog = showLog;  
	}
}
