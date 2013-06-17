package com.diyapp.kreator2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.diyapp.lib.DiyDbAdapter;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppWidgetProvider {

	static String top = ( "g�ra");
	static String[] opisy = { "opis1", "opis2", "opis3",
		"opis4", "akcaj5", "akcja6" };
	static String [] aktywnosci = { "kacja1", "akcja2", "akcja3",
		"4", "akcaj5", "akcja6" };
	static Context mycontext = null;
	static RemoteViews myviews = null;
	static DiyDbAdapter mDbHelper;
	public static void addOpis(String s){
		
	}
	public static void addAktywnosc(String s) {
		
	}
	 @Override
	 public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	   int[] appWidgetIds) {
		 MainActivity.mycontext = context;
//Timer timer = new Timer();
//timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 1000);
	  RemoteViews views = new RemoteViews(context.getPackageName(),
	    R.layout.activity_main);
	  
	  
	  Context ctx = null;
		try {
			// creating context from mainAPP for accessing database
			ctx = mycontext.createPackageContext("com.diyapp.kreator2",
					Context.CONTEXT_IGNORE_SECURITY);
			if (ctx == null) {
				Log.v("fake", "failed to get db");
			} else {
				Log.v("fake", "got db");
			}
		} catch (PackageManager.NameNotFoundException e) {
			// package not found
			Log.e("Error", e.getMessage());
		}
System.out.println("ONUPDATE");
		mDbHelper = new DiyDbAdapter(ctx);
		mDbHelper.open();
	  
	  int i =0;
	  Cursor c = mDbHelper.fetchAllDiy();
		if (c.moveToFirst()) {
			do {
				for (String column : DiyDbAdapter.COLUMNS) {
					
						opisy[i] = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_DESCRIPTION));
						aktywnosci[i] = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TITLE));
						//SSID_szukane = c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID));
						

				}
				i++;
			} while (c.moveToNext());
		}
	  
      views.setTextViewText(R.id.widget_textview0, top);
      views.setTextViewText(R.id.widget_textview1, "opis pocz�tkowy");
      views.setTextViewText(R.id.widget_textview2, aktywnosci[0]);
      views.setTextViewText(R.id.widget_textview3, aktywnosci[1]);
      views.setTextViewText(R.id.widget_textview4, aktywnosci[2]);
      views.setTextViewText(R.id.widget_textview5, aktywnosci[3]);
	  views.setOnClickPendingIntent(R.id.widget_textview2,
	    buildButtonPendingIntent(context, 0));
	  views.setOnClickPendingIntent(R.id.widget_textview3,
			    buildButtonPendingIntent(context, 1));
	  views.setOnClickPendingIntent(R.id.widget_textview4,
			    buildButtonPendingIntent(context, 2));
	  views.setOnClickPendingIntent(R.id.widget_textview5,
			    buildButtonPendingIntent(context, 3));

	  pushWidgetUpdate(context, views);
	 }
	 
	 
	 public static void setActivity(Intent intent, Context context) {
		  Bundle extras = intent.getExtras();
		  String str = null;
		  if (intent.getAction().matches("CHANGE_ACTVITYINTABLE")) {
			  int cos = intent.getFlags();
			  str = extras.getString("nr");
			  //aktywnosci[cos] = str;
		  }
		  RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				    R.layout.activity_main);
				  remoteViews.setTextViewText(R.id.widget_textview0, str);
		  pushWidgetUpdate(context, remoteViews);
		  Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		 }
//	 private static void WidgetUpdate() {
//
//		 pushWidgetUpdate(mycontext, myviews);
//	 }

	 public static PendingIntent buildButtonPendingIntent(Context context, int i) {
	  Intent intent = new Intent();
	  intent.setAction("CHANGE_TEXT" + Integer.toString(i));
	  //intent.addFlags(i);
	  intent.putExtra("nr" + Integer.toString(i), aktywnosci[i]);
	  return PendingIntent.getBroadcast(context, 0, intent,
	    PendingIntent.FLAG_CANCEL_CURRENT);
	 }
	 
	 public static PendingIntent buildButtonPendingIntentXXX(Context context, int i) {
		  Intent intent = new Intent();
		  intent.setAction("CHANGE_ACTIVITIES");
		  return PendingIntent.getBroadcast(context, 0, intent,
		    PendingIntent.FLAG_CANCEL_CURRENT);
		 }

	 public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
	  ComponentName myWidget = new ComponentName(context, MainActivity.class);
	  AppWidgetManager manager = AppWidgetManager.getInstance(context);
	  manager.updateAppWidget(myWidget, remoteViews);
	 }
	 
	 //Poni�ej do wyrzucenia - to tylko czas
//	 private class MyTime extends TimerTask {
//			RemoteViews remoteViews;
//			AppWidgetManager appWidgetManager;
//			ComponentName thisWidget;
//			DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
//			
//		public MyTime(Context context, AppWidgetManager appWidgetManager) {
//			this.appWidgetManager = appWidgetManager;
//			remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_main);
//			thisWidget = new ComponentName(context, MainActivity.class);
//		}
//		
//		@Override
//		public void run() {
//			remoteViews.setTextViewText(R.id.widget_textview1, "TIME = " +format.format(new Date()));
//			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
//		}
//			
//		}

	}
