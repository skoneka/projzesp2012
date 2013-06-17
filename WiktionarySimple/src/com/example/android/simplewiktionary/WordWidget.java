/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.simplewiktionary;

import com.diyapp.lib.DiyDbAdapter;

import com.example.android.simplewiktionary.SimpleWikiHelper.ApiException;
import com.example.android.simplewiktionary.SimpleWikiHelper.ParseException;
import com.example.android.simplewiktionary2.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Define a simple widget that shows the Wiktionary "Word of the day." To build
 * an update we spawn a background {@link Service} to perform the API queries.
 */
public class WordWidget extends AppWidgetProvider {
	private static int count;
	private PendingIntent service = null;
	
    @Override
    public void onDisabled(Context context)
    {
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        m.cancel(service);
    }
	
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        // To prevent any ANR timeouts, we perform the update in a service
        System.out.println("onUpdate()");
        //context.startService(new Intent(context, UpdateService.class));
        
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final Calendar TIME = Calendar.getInstance();
        TIME.set(Calendar.MINUTE, 0);
        TIME.set(Calendar.SECOND, 0);
        TIME.set(Calendar.MILLISECOND, 0);

        final Intent i = new Intent(context, UpdateService.class);

        if (service == null)
        {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000 * 60, service);
    }

    public static class UpdateService extends Service {
    	static DiyDbAdapter mDbHelper;
        @Override
        public void onStart(Intent intent, int startId) {
        	
    		Context ctx = null;
    		try {
    			// creating context from mainAPP for accessing database
    			ctx = createPackageContext("com.diyapp.kreator2",
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
            
            mDbHelper = new DiyDbAdapter(ctx);
            mDbHelper.open();
            System.out.println("onStart()");
            // Build the widget update for today
            RemoteViews updateViews = buildUpdate(this);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, WordWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }
        /*
        @Override
        public int onStartCommand(Intent intent, int flags, int startId)
        {
        	System.out.println("onStartCommand()");
            buildUpdate();

            return super.onStartCommand(intent, flags, startId);
        }*/

        /**
         * Build a widget update to show the current Wiktionary
         * "Word of the day." Will block until the online API returns.
         */
        
        public RemoteViews buildUpdate(Context context) {
            System.out.println("RemoteViews buildUpdate()");
            String lastUpdated = DateFormat.format("MMMM dd, yyyy h:mm:ssaa", new Date()).toString();
        	RemoteViews updateViews = null;
            updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_message);
            updateViews.setTextViewText(R.id.message, lastUpdated + "\n" + mDbHelper.getServiceMsg());
            return updateViews;
        }
        /*
        public void buildUpdate() {
            System.out.println("void buildUpdate()");
            String lastUpdated = DateFormat.format("MMMM dd, yyyy h:mmaa", new Date()).toString();
        	RemoteViews updateViews = null;
            updateViews = new RemoteViews(getPackageName(), R.layout.widget_message);
            String uri = "http://www.google.pl/";
            updateViews.setTextViewText(R.id.message, "---" + lastUpdated + "\n" + Integer.toString(WordWidget.count++) + "\n" + urlGet(String.valueOf(uri)));
            
            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(this, WordWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, updateViews);
        }
        */
        
/*
        public RemoteViews buildUpdate(Context context) {
            RemoteViews updateViews = null;
                updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_message);
                String uri = "http://www.google.pl/";
                updateViews.setTextViewText(R.id.message, Parse(urlGet(String.valueOf(uri))));
                return updateViews;
                
        }
        */
        
		private String urlGet(String urlString) {

			URLConnection urlConnection = null;
			URL url = null;
			String string = null;

			try {
				url = new URL(urlString);
				urlConnection = url.openConnection();

				InputStream inputStream = urlConnection.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader reader = new BufferedReader(inputStreamReader);

				StringBuffer stringBuffer = new StringBuffer();

				while ((string = reader.readLine()) != null) {
					stringBuffer.append(string + "\n");
				}
				inputStream.close();

				string = stringBuffer.toString();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return string;
		}

		private CharSequence Parse(String myString) {
			CharSequence title = myString.subSequence(
					myString.indexOf("<title>") + 7,
					myString.indexOf("</title>"));
			return title;
		}
        
        @Override
        public IBinder onBind(Intent intent) {
            System.out.println("onBind()");
            // We don't need to bind to this service
            return null;
        }
    }
}
