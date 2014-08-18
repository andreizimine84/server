package com.helloworld;

import java.io.InputStream;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.text.format.Time;

/**
 * 
 * @author andreizimine
FileService med SeleceAndShareService klasserna skapar med hjälp av broadcastreceivers från MainActivity 
OnCreate metoden en alarm funktionalitet då FileService skickar filerna till server varje t.ex minut och 
SelectAndShareService som skapar filerna i intern minne på telefonen. Båda service använder sig av 
hjälpklasser i form av objekt och dessa i behov anropar andra objekt som dem behöver hjälp med att handskas 
med t. ex offline funtionalitet.  
 *
 * 
 */

public class FileService extends IntentService {

	String dirName = "tempDirectory";
	byte[] buffer = null;
	InputStream is = null;

	public FileService(){
		super("FileService");
	}

	protected void onHandleIntent(Intent intent) throws NullPointerException{
		FileSender.main(intent, this.getApplicationContext());
		scheduleNextUpdate();
	}

	public void scheduleNextUpdate()
	{
		Intent intent = new Intent(this, this.getClass());
		PendingIntent pendingIntent =
				PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		long currentTimeMillis = System.currentTimeMillis();
		long nextUpdateTimeMillis = currentTimeMillis + 1 * DateUtils.MINUTE_IN_MILLIS;
		Time nextUpdateTime = new Time();
		nextUpdateTime.set(nextUpdateTimeMillis);

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
	}
}
