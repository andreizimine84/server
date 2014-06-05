package com.helloworld; 

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;

//import java.io.FileInputStream;
import java.io.InputStream;


public class MainClient{
	
	static InputStream is = null;

	public static void main(String[] args) throws Exception 
	{
		System.out.println("hello");
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		ContentExchange contentExchange = new ContentExchange();
		httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		contentExchange.setMethod("POST");
		IDataGenerator generator = new DefaultDataGenerator();
		is = generator.getData();
		contentExchange.setRequestContentSource(is);
		contentExchange.setURL("http://localhost:8080");
		httpClient.send(contentExchange);
		contentExchange.waitForDone();	
		System.err.println("Response status: "
				+ contentExchange.getResponseStatus());
	}
}
	
