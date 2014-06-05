package com.helloworld;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainAClient {
	
	static String DEBUG_TAG = "MainActivity";
	HttpURLConnection conn = null;
	static byte[] buffer = null;
	static InputStream is = null;
	
	public static void main(String[] args) throws IOException
	{
		
		IDataGenerator generator = new DefaultDataGenerator();
		is = generator.getData();

		URL url = new URL("http://localhost:8080");

		String charset = "UTF-16";

		int size = is.available();
		byte[] query = new byte[size];
		
		int nRead;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {

			while ((nRead = is.read(query, 0, query.length)) != -1)
			{
				  baos.write(query, 0, nRead);
			}

				baos.flush();

		 }
		 catch (IOException e) {
					e.printStackTrace();
		 }

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Accept-Charset", charset);
		conn.setRequestProperty("ENCTYPE", "multipart/form-data");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		OutputStream output = conn.getOutputStream();
		
		try 
		{
			System.out.println(baos.toByteArray().length);
		    output.write(baos.toByteArray());
		} 
		finally 
		{
		     try 
		     { 
		    	 output.close(); 
		     } 
		catch (IOException logOrIgnore) {}
		conn.getInputStream();
		}	

	}

}


