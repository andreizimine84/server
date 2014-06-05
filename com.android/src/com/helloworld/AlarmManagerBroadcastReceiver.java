package com.helloworld;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver{

	final public static String ONE_TIME = "onetime";
	//Context.registerReceiver()
	//Context.unregisterReceiver()
	//If the event for which the broadcast receiver has registered happens, the onReceive() method of the receiver is called by the Android system.
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	    Intent intent2 = new Intent(context, FileService.class);
	    context.startService(intent2);
	}

	//After the onReceive() of the receiver class has finished, the Android system is allowed to recycle the receiver.
	//If you have potentially long running operations, you should trigger a service instead.
	//If the phone receives a phone call, then our receiver will be notified and log a message.
	public void SetAlarm(Context context)
	{
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		//After after 5 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
	}

	public void CancelAlarm(Context context)
	{
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	public void setOnetimeTimer(Context context){
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		intent.putExtra(ONE_TIME, Boolean.TRUE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}
	
}
