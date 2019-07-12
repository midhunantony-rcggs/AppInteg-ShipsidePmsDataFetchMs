package com.rcyc.ship.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("unused") 
public class VMWareConnection {

	private String vmWareUrlwithPort = System.getenv("VMWARE_IP_PORT");

	private final Logger log = LoggerFactory.getLogger(VMWareConnection.class);

	public HttpURLConnection VMWareConnection(String apiUrl) throws IOException, ConnectException {
		log.debug("VMWareConnection() started ******");

		URL url = new URL("http://"+vmWareUrlwithPort+"/"+ apiUrl);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(EnvironmentConstants.CONNECTION_TIMEOUT);
		connection.setReadTimeout(EnvironmentConstants.READ_TIMEOUT);
		
		log.debug("VMWareConnection() ended ******");
		return connection;
	}

	public boolean sendRequestToVmWare(String apiUrl, String body) throws IOException {
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
}
