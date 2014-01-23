package com.helloworld; 

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

import java.io.FileInputStream;


public class MainClient{
	
	static FileInputStream fis = null;

	public static void main(String[] args) throws Exception 
	{
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentExchange contentExchange = new ContentExchange();
		httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		contentExchange.setMethod("POST");	
		fis = new FileInputStream("Koder0");
		contentExchange.setRequestContentSource(fis);
		contentExchange.setURL("http://localhost:8080");
		httpClient.send(contentExchange);
		contentExchange.waitForDone();	
		System.err.println("Response status: "
				+ contentExchange.getResponseStatus());
	}
}
	
