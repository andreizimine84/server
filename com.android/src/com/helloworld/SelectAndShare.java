package com.helloworld;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v4.content.LocalBroadcastManager;
// WIFI// Context.MODE_APPEND MODE_WORLD_WRITEABLE MODE_WORLD_READABLE
public class SelectAndShare {
	FileOutputStream fos = null;
	ByteArrayOutputStream baos = null;
	InputStream is = null;
	int bytesAvailable = 0;
	byte[] buffer = null;
	static Context globalContext;
	
	public static void main(Intent intent, Context context)
	{
		globalContext = context;
		TempFolder.setFolder(globalContext.getFilesDir().getPath());
		TempFolder.setFile(completeWriteTempFile(getInfoStream()));
		Intent in1 = new Intent("byteArray");
		sendLocationBroadcast(in1);
	}
	
	public static String completeWriteTempFile(ByteArrayOutputStream baos) 
	{
		
		FileOutputStream outputStream = null;
	    try
	    {
	    	TempFolder.mAddNumber += 1;
    		if(TempFolder.mAddNumber != 0) 	
    			outputStream = globalContext.openFileOutput("output_temp" + TempFolder.mAddNumber + ".txt", Context.MODE_PRIVATE);
    		else
    			throw new EmptyStackException();
    	
	    	baos.writeTo(outputStream);

	    	return "output_temp" + TempFolder.mAddNumber + ".txt";
	    }
    
		catch(IOException e)
		{
			System.err.println("Caught IOException: " + e.getMessage());
		}
	    return null;
	}
	
	private static void sendLocationBroadcast(Intent intent)
	{
		intent.putExtra("numberFile", TempFolder.getFile());
		intent.putExtra("folder", TempFolder.getFolder());
	    LocalBroadcastManager.getInstance(globalContext).sendBroadcast(intent);
	}

	public static List<RunningAppProcessInfo> getSystemService()
	{
		ActivityManager manager = (ActivityManager) globalContext.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningAppProcessInfo> android = manager.getRunningAppProcesses();
	
		return android;
	}

	public static ByteArrayOutputStream getInfoStream()
	{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Iterator<RunningAppProcessInfo> iterator = getSystemService().iterator();
		
		ActivityManager manager = (ActivityManager) globalContext.getSystemService(Context.ACTIVITY_SERVICE);
		
		try {
			
			while (iterator.hasNext())
			{
				RunningAppProcessInfo version = iterator.next();
				
				int numb = 0;
				
				Debug.MemoryInfo[] memoryInfos = manager.getProcessMemoryInfo(new int[]{version.pid});
				System.out.println(memoryInfos[numb].getTotalPss());
				String str = version.processName + memoryInfos[numb].getTotalPss() + "\n";
				baos.write(str.getBytes());

				numb++;
				iterator.remove();
			}

				baos.flush();

		 }
		 catch (IOException e) {
			e.printStackTrace();
		 }
		
		return baos;
	}
}
