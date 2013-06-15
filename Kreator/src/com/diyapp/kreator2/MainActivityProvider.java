package com.diyapp.kreator2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.diyapp.kreator2.R;

public class MainActivityProvider extends BroadcastReceiver {


	 @Override
	 public void onReceive(Context context, Intent intent) {
	  if (intent.getAction().matches("CHANGE_TEXT2"))
		  updateWidgetText(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT3"))
		  updateWidgetText(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT4"))
		  updateWidgetText(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT5"))
		  updateWidgetText(context, intent);
	  else if (intent.getAction().matches("CHANGE_TEXT6"))
		  updateWidgetText(context, intent);
	 }

	 private void updateWidgetText(Context context, Intent intent) {
	  RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
	    R.layout.activity_main);
	  remoteViews.setTextViewText(R.id.widget_textview0, getTextToSet(intent, context));
	  // remoteViews.setOnClickPendingIntent(R.id.wid_button,
	  // MainActivity.buildButtonPendingIntent(context));

	  MainActivity.pushWidgetUpdate(context.getApplicationContext(),
	    remoteViews);
	 }

	 private CharSequence getTextToSet(Intent intent, Context context) {
	  Bundle extras = intent.getExtras();
	  String str = null;
	  if (intent.getAction().matches("CHANGE_TEXT2"))
		  str = extras.getString("nr" + 2);
	  else if (intent.getAction().matches("CHANGE_TEXT3"))
		  str = extras.getString("nr" + 3);
	  else if (intent.getAction().matches("CHANGE_TEXT4"))
		  str = extras.getString("nr" + 4);
	  else if (intent.getAction().matches("CHANGE_TEXT5"))
		  str = extras.getString("nr" + 5);
	  else if (intent.getAction().matches("CHANGE_TEXT6"))
		  str = extras.getString("nr" + 6);
	  //int flag = extras.getInt("nr" + Integer.toString());
	  Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	  return "Dzia�a: " + str;
	 }
	 
	 public void setText( String str) {
		 String string = str;
		 
	 }
}