package com.diyapp.kreator2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MainActivity extends AppWidgetProvider {

	static String [] napisy = { "Test message0", "Test message1", "Test message2",
			"", "Test message4", "Test message5" };
	//public void setTexts
	 @Override
	 public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	   int[] appWidgetIds) {

Timer timer = new Timer();
timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 1000);
	  RemoteViews views = new RemoteViews(context.getPackageName(),
	    R.layout.activity_main);
      views.setTextViewText(R.id.widget_textview0, napisy[0]);
      views.setTextViewText(R.id.widget_textview1, napisy[1]);
      views.setTextViewText(R.id.widget_textview2, napisy[2]);
      views.setTextViewText(R.id.widget_textview3, napisy[3]);
      views.setTextViewText(R.id.widget_textview4, napisy[4]);
      views.setTextViewText(R.id.widget_textview5, napisy[5]);
	  views.setOnClickPendingIntent(R.id.widget_textview2,
	    buildButtonPendingIntent(context, 2));
	  views.setOnClickPendingIntent(R.id.widget_textview3,
			    buildButtonPendingIntent(context, 3));
	  views.setOnClickPendingIntent(R.id.widget_textview4,
			    buildButtonPendingIntent(context, 4));
	  views.setOnClickPendingIntent(R.id.widget_textview5,
			    buildButtonPendingIntent(context, 5));

	  pushWidgetUpdate(context, views);
	 }

	 public static PendingIntent buildButtonPendingIntent(Context context, int i) {
	  Intent intent = new Intent();
	  intent.setAction("CHANGE_TEXT" + Integer.toString(i));
	  //intent.addFlags(i);
	  intent.putExtra("nr" + Integer.toString(i), i);
	  intent.putExtra("nr" + Integer.toString(i), napisy[i]);
	  return PendingIntent.getBroadcast(context, 0, intent,
	    PendingIntent.FLAG_UPDATE_CURRENT);
	 }

	 public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
	  ComponentName myWidget = new ComponentName(context, MainActivity.class);
	  AppWidgetManager manager = AppWidgetManager.getInstance(context);
	  manager.updateAppWidget(myWidget, remoteViews);
	 }
	 
	 //Poni�ej do wyrzucenia - to tylko czas
	 private class MyTime extends TimerTask {
			RemoteViews remoteViews;
			AppWidgetManager appWidgetManager;
			ComponentName thisWidget;
			DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
			
		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_main);
			thisWidget = new ComponentName(context, MainActivity.class);
		}
		
		@Override
		public void run() {
			remoteViews.setTextViewText(R.id.widget_textview1, "TIME = " +format.format(new Date()));
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		}
			
		}

	}
