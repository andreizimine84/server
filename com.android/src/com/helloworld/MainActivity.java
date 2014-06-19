package com.helloworld;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, Runnable
{
	Button clickButton;
	String urlString = "http://10.0.2.2:8080";
	private AlarmManagerBroadcastReceiver alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		enableStrictMode(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickButton = (Button) findViewById(R.id.button1);
		clickButton.setOnClickListener(this);
		alarm = new AlarmManagerBroadcastReceiver();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		if (BuildConfig.DEBUG) {
		      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		            .detectDiskReads()
		            .detectDiskWrites()
		            .detectNetwork()
		            .penaltyLog()
		            .build());
		      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		            .detectAll()
		            .penaltyLog()
		             .build());
		}
		Log.d("client", "onCreate!!");
	}

	 protected void onStart() {
		  super.onStart();
	 }
	
	 @Override
	 public void onResume() {
	   super.onResume();

	   // Register mMessageReceiver to receive messages.
	   LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
	       new IntentFilter("my-event"));
	 }

	 // handler for received Intents for the "my-event" event 
	 private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	   @Override
	   public void onReceive(Context context, Intent intent) {
	     // Extract data included in the Intent
	     String message = intent.getStringExtra("message");
	     Log.d("receiver", "Got message: " + message);
	   }
	 };


	// Send an Intent with an action named "my-event". 
	private void sendMessage() {
	  Intent intent = new Intent(this, FileService.class);
	  //startService(intent);
	  // add data
	  //intent.putExtra("message", "data");
	  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	} 
	 
	 @Override
	 protected void onPause() {
	   // Unregister since the activity is not visible
	   LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
	   super.onPause();
	 } 
	 
	 public void startRepeatingTimer() {
		 Context context = this.getApplicationContext();
		 if(alarm != null){
			 alarm.SetAlarm(context);
		 }else{
			 Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
		 }
	 }

	 public void cancelRepeatingTimer(){
		 Context context = this.getApplicationContext();
		 if(alarm != null){
			 alarm.CancelAlarm(context);
		 }else{
			 Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
		 }
	 }

	 public void onetimeTimer(View view){
		 Context context = this.getApplicationContext();
		 if(alarm != null){
			 alarm.setOnetimeTimer(context);
		 }else{
			 Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
		 }
	 }

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	public void onClick(View v){
		Intent intent = new Intent(this, FileService.class);
		//startRepeatingTimer();
		//run(intent);
		//cancelRepeatingTimer();
		onPause();
		sendMessage();
		onResume();
		startService(intent);
		Log.d("client", "button clicked!!");
	}

	public FileOutputStream getStream(String path) throws FileNotFoundException {
        return openFileOutput(path, Context.MODE_PRIVATE);
    }
		
	public static void enableStrictMode(Context context) {
	    StrictMode.setThreadPolicy(
	    new StrictMode.ThreadPolicy.Builder()
	    .detectDiskReads()
	    .detectDiskWrites()
	    .detectNetwork()
	    .penaltyLog()
	    .build());
	    StrictMode.setVmPolicy(
	    new StrictMode.VmPolicy.Builder()
	    .detectLeakedSqlLiteObjects()
	    .penaltyLog()
	    .build());
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) 
	      getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 
	
	public static String getFileName(Context context){
		String FILENAME = "output_" + context.hashCode() + ".txt";
		return FILENAME;
	}

	public void run(final Intent intent) {
		
		Thread thread = new Thread()
		
		{
		    @Override
		    public void run() {
		        try {
		    		startService(intent);
		            while(true) {
		                sleep(1000);
		            }
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		};

		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}