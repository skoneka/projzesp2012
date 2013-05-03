package com.example.fakediya;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TestReceiver extends android.content.BroadcastReceiver {
	  @Override
	  public void onReceive(Context context, Intent intent) {
		Log.d("blah","onReceive");
		Toast toast = Toast.makeText(context, "Received message",	Toast.LENGTH_LONG);
		toast.show();
	  }
}