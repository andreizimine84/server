package com.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartReceiver extends BroadcastReceiver {

@Override
  public void onReceive(Context context, Intent intent) {
		  Intent intent1 = new Intent(context, SelectAndShareService.class);
		  context.startService(intent1);
  } 
} 
