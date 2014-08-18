package com.helloworld;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.support.v4.content.LocalBroadcastManager;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

 
public class MainActivity extends Activity implements OnClickListener, Runnable
{
	Button clickButton;
	String urlString = "http://10.0.2.2:8080";

	private BroadcastReceiver mStartReceiver;
	private BroadcastReceiver mBreakReceiver;
	private IntentFilter mStartFilter;
	private IntentFilter mBreakFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		enableStrictMode(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		mStartFilter = new IntentFilter();
		mStartFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mBreakFilter = new IntentFilter();
		mBreakFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		
		mStartReceiver = new BroadcastReceiver() {
			  public void onReceive(Context context, Intent intent) {
					  Intent intent1 = new Intent(context, SelectAndShareService.class);
					  context.startService(intent1); 
			  } 
		};

		LocalBroadcastManager.getInstance(this).registerReceiver(mBreakReceiver2, new IntentFilter("byteArray"));
		
		mBreakReceiver = new BroadcastReceiver() {
			  public void onReceive(Context context, Intent intent) {
					  Intent intent1 = new Intent(context, FileService.class);
					  context.startService(intent1);
			  } 
		};	

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
	}

	public BroadcastReceiver mBreakReceiver2 = new BroadcastReceiver() {
		@Override
	    public void onReceive(Context context, Intent intent) {
			TempFolder.mAddBackNumber = TempFolder.mAddNumber;				
			Intent intent2 = new Intent(context, FileService.class);
			intent2.putExtra("image", intent.getStringExtra("folder"));
			intent2.putExtra("image", intent.getStringExtra("numberFile"));
			TempFolder.connectRWException = true;
			context.startService(intent2);
	    }
	};
	
	 protected void onStart() {
		  super.onStart();
	 }
	
	 @Override
	 public void onResume() {
	   super.onResume();
	   registerReceiver(mStartReceiver, mStartFilter);
	   registerReceiver(mBreakReceiver, mBreakFilter);
	 }

	 public void enableStartBroadcastReceiver(){

		 ComponentName receiver = new ComponentName(this, MyStartReceiver.class);

		 PackageManager pm = this.getPackageManager();

		 pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	 }
	
	 public void disableStartBroadcastReceiver(){

		 ComponentName receiver = new ComponentName(this, MyStartReceiver.class);

		 PackageManager pm = this.getPackageManager();

		 pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

	 } 

	 @Override
	 protected void onPause() {
	
	   super.onPause();
	   unregisterReceiver(mStartReceiver);
	   unregisterReceiver(mBreakReceiver);
	
	 } 
		 	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}