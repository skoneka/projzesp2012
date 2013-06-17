package com.diyapp.kreator2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.diyapp.kreator2.R;

public class MainActivityProvider extends BroadcastReceiver {

private static int i = 0;
private static int j = 0;
	 @Override
	 public void onReceive(Context context, Intent intent) {
		 
	  if (intent.getAction().matches("CHANGE_TEXT0"))
		  updateWidgetDescription(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT1"))
		  updateWidgetDescription(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT2"))
		  updateWidgetDescription(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT3"))
		  updateWidgetDescription(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT4"))
		  updateWidgetDescription(context, intent);
	  else if (intent.getAction().matches("CHANGE_ACTIVITIES"))
		  updateWidgetActivities(context);
	  else if (intent.getAction().matches("CHANGE_ACTVITYINTABLE")) {
		  System.out.println("o receiv");
		  MainActivity.setActivity(intent,  context);
		  
	  }

	 }

	 private void updateWidgetDescription(Context context, Intent intent) {
	  RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	    R.layout.activity_main);
	  remoteViews.setTextViewText(R.id.widget_textview0, getTextToSet(intent, context));
	  remoteViews.setTextViewText(R.id.widget_textview1, getDescriptionToSet(intent, context));
	  // remoteViews.setOnClickPendingIntent(R.id.wid_button,
	  // MainActivity.buildButtonPendingIntent(context));
	  //MainActivity.myviews = remoteViews;
	  MainActivity.pushWidgetUpdate(context.getApplicationContext(),
	    remoteViews);
	 }
	 
	 private static void updateWidgetActivities(Context context) {
		 RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				    R.layout.activity_main);
		 Toast.makeText(context, MainActivity.aktywnosci[3], Toast.LENGTH_SHORT).show();
		 remoteViews.setTextViewText(R.id.widget_textview2, MainActivity.aktywnosci[0]);
		 remoteViews.setTextViewText(R.id.widget_textview3, MainActivity.aktywnosci[1]);
		 remoteViews.setTextViewText(R.id.widget_textview4, MainActivity.aktywnosci[2]);
		 remoteViews.setTextViewText(R.id.widget_textview5, MainActivity.aktywnosci[3]);
		 MainActivity.pushWidgetUpdate(context.getApplicationContext(),
				    remoteViews);
	 }

//	 private CharSequence getTextToSet(Intent intent, Context context) {
//	  Bundle extras = intent.getExtras();
//	  String str = null;
//	  if (intent.getAction().matches("CHANGE_TEXT0"))
//		  str = MainActivity.aktywnosci[0];
//	  else if (intent.getAction().matches("CHANGE_TEXT1"))
//		  str = MainActivity.aktywnosci[1];
//	  else if (intent.getAction().matches("CHANGE_TEXT2"))
//		  str = MainActivity.aktywnosci[2];
//	  else if (intent.getAction().matches("CHANGE_TEXT3"))
//		  str = MainActivity.aktywnosci[3];
//	  else if (intent.getAction().matches("CHANGE_TEXT4"))
//		  str = MainActivity.aktywnosci[4];
//	  //int flag = extras.getInt("nr" + Integer.toString());
//	  Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//	  return "Dzia�a: " + str;
//	 }
	 
	 private CharSequence getTextToSet(Intent intent, Context context) {
		  Bundle extras = intent.getExtras();
		  String str = null;
		  System.out.println("SETTEXT");
		  //str = extras.getString("nr");		 
		  if (intent.getAction().matches("CHANGE_TEXT0"))
			  str = MainActivity.aktywnosci[0];
		  else if (intent.getAction().matches("CHANGE_TEXT1"))
			  str = MainActivity.aktywnosci[1];
		  else if (intent.getAction().matches("CHANGE_TEXT2"))
			  str = MainActivity.aktywnosci[2];
		  else if (intent.getAction().matches("CHANGE_TEXT3"))
			  str = MainActivity.aktywnosci[3];
		  else if (intent.getAction().matches("CHANGE_TEXT4"))
			  str = MainActivity.aktywnosci[4];
		  //int flag = extras.getInt("nr" + Integer.toString());
		  Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		  return "Dzia�a: " + str;
		 }
	 
	 private CharSequence getDescriptionToSet(Intent intent, Context context) {
		  Bundle extras = intent.getExtras();
		  String str = null;
		  if (intent.getAction().matches("CHANGE_TEXT0"))
			  str = MainActivity.opisy[0];
		  else if (intent.getAction().matches("CHANGE_TEXT1"))
			  str = MainActivity.opisy[1];
		  else if (intent.getAction().matches("CHANGE_TEXT2"))
			  str = MainActivity.opisy[2];
		  else if (intent.getAction().matches("CHANGE_TEXT3"))
			  str = MainActivity.opisy[3];
		  else if (intent.getAction().matches("CHANGE_TEXT4"))
			  str = MainActivity.opisy[4];
		  //int flag = extras.getInt("nr" + Integer.toString());
		 //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		  return str;
		 }
	 
	 private String[] getActivitiesToSet( Intent intent, Context context ) {
		 Bundle extras = intent.getExtras();
		 String[] str = null;
		 if(intent.getAction().equals("CHANGE_ACTIVITIES"))
			 str = extras.getStringArray("activities");
		// Toast.makeText(context, "cos tam", Toast.LENGTH_SHORT).show();
		 return str;
	 }
}