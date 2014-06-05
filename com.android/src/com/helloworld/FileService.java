package com.helloworld;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class FileService extends IntentService{
	
	public FileService(){
		super("FileService");
	}
	
	//AlarmManager skapar trådar automatiskt i onResieve
	
	protected void onHandleIntent(Intent intent) {
	    String filename = "myfile";
	    String outputString = "Hello world!";
	    URL url;
	    try {
	        FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
	        outputStream.write(outputString.getBytes());
	        outputStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    try {
	        FileInputStream inputStream = openFileInput(filename);
	        /*BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
	        StringBuilder total = new StringBuilder();
	        String line;
	        while ((line = r.readLine()) != null) {
	            total.append(line);
	        }*/
			url = new URL("http://10.0.2.2:8080");
			
			String charset = "UTF-8";

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			OutputStream output = conn.getOutputStream();
			
			output.write(getStream(inputStream).toByteArray());
			
			output.close();
		
			conn.getInputStream();
	        
	        //r.close();
	        inputStream.close();
	        //Log.d("File", "File contents: " + total);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    publishResults();
	}

	  private void publishResults() {
		    Intent intent = new Intent(Intent.ACTION_SEND);
		    //intent.putExtra(FILEPATH, outputPath);
		    //intent.putExtra(Intent.EXTRA_STREAM, result);
		    sendBroadcast(intent);
	  }
	
	public ByteArrayOutputStream getStream (InputStream is) throws IOException
	{
		
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
		
		return baos;
	}
	
}
