package com.rcyc.ship.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rcyc.ship.dto.PMSDataRequest;

@Configuration
@SuppressWarnings("unused")
public class VMWareConnection {

	private String vmWareUrlwithPort = System.getenv("VMWARE_IP_PORT");

//	private String vmWareUrlwithPort = "localhost:8090";

	private final Logger log = LoggerFactory.getLogger(VMWareConnection.class);

	public HttpURLConnection VMWareConnection(String apiUrl) throws IOException, ConnectException {
		log.debug("VMWareConnection() started ******");

		URL url = new URL("http://" + vmWareUrlwithPort + "/" + apiUrl);
		System.out.println(apiUrl);
//		URL url = new URL("http://localhost:8090/"+ apiUrl);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty( "charset", "utf-8");
	
		connection.setConnectTimeout(EnvironmentConstants.CONNECTION_TIMEOUT);
		connection.setReadTimeout(EnvironmentConstants.READ_TIMEOUT);

		log.debug("VMWareConnection() ended ******");
		return connection;
	}
	
	public boolean sendRequestToVmWare(String apiUrl, String body) throws IOException {
		log.info("sendRequestToVmWare");
		boolean status = true;
		HttpURLConnection connection = VMWareConnection(apiUrl);
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();
	    int responseCode = connection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) { //success
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        		connection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = in .readLine()) != null) {
	            response.append(inputLine);
	        } in .close();
	        // print result
	        System.out.println(response.toString());
	    }
		return status;

	}

	public boolean sendRequestToVmWare(String apiUrl, List<PMSDataRequest> PmsDataRequestList,String msgId) throws IOException, URISyntaxException {
		System.out.println("sendRequestToVmWare");
		boolean status = true;
	    RestTemplate restTemplate = new RestTemplate();
//	     
	    URI url = new URI("http://" + vmWareUrlwithPort + "/" + apiUrl);
	     
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("messageIdentifier",msgId);   
	 
	    HttpEntity<List> request = new HttpEntity<>(PmsDataRequestList, headers);
	 
	    ResponseEntity<String> result = restTemplate.postForEntity(url,request, String.class);
		return status;

	}
}
