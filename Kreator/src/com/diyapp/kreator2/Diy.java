/*
 * Copyright (C) 2008 Google Inc.
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

package com.diyapp.kreator2;

import com.diyapp.kreator2.R;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


//importy z service

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.diyapp.lib.DiyDbAdapter;
import pl.diya.execute2.IRemoteService;

public class Diy extends ListActivity {
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DiyDbAdapter mDbHelper;
	
	//uzywanie service
	IRemoteService mRemoteService;
	
	
		
	private ServiceConnection mServiceConnection=new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// get instance of the aidl binder
			mRemoteService = IRemoteService.Stub.asInterface(service);
			try {
				String message=mRemoteService.sayHello("Kamyk");
				Log.v("message", message);
			} catch (RemoteException e) {
				Log.e("RemoteException", e.toString());
			}

		}
	};
	
	
	//koniec uzywania service

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.diys_list);
		mDbHelper = new DiyDbAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(getListView());
		String[] fileList = fileList();

		for (String string : fileList) {
			Log.v("diy", string);
		}
		Log.v("diy", "kreator");

		//if(!mDbHelper.getServiceStatus()){
			
			System.out.println("Service nie w�aczony");
		Intent serviceIntent=new Intent();
		
        serviceIntent.setClassName("pl.diya.execute2", "pl.diya.execute2.Execute");
        
        stopService(serviceIntent);
        
        boolean ok=bindService(serviceIntent, mServiceConnection,Context.BIND_AUTO_CREATE);
        if(ok)
        	mDbHelper.setServiceStatus(true);
        Log.v("ok", String.valueOf(ok));
        
        //}
		
        //else
		//	System.out.println("Service w��czony");

		System.out.println("Drugi raz:");
		//isMyServiceRunning();
		

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mDbHelper != null)
			mDbHelper.close();
	}
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	System.out.println(service.service.getClassName());
	        if ("pl.diya.execute2.Execute".equals(service.service.getClassName())) {
	        	System.out.println("Equals");
	            return true;
	        }
	    }
	    return false;
	}
	private void fillData() {
		// Get all of the rows from the database and create the item list
		Cursor diysCursor = mDbHelper.fetchAllDiy();
		startManagingCursor(diysCursor);

		// Create an array to specify the fields we want to display in the list
		// (only TITLE)
		String[] from = new String[] { DiyDbAdapter.KEY_TITLE };

		// and an array of the fields we want to bind those fields to (in this
		// case just text1)
		int[] to = new int[] { R.id.text1 };

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter diys = new SimpleCursorAdapter(this,
				R.layout.diys_row, diysCursor, from, to);
		setListAdapter(diys);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createDiy();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);

		// TODO: fill in rest of method
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			mDbHelper.deleteDiy(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);

		// TODO: fill in rest of method
	}

	private void createDiy() {
		// TODO: fill in implementation
		// Intent i = new Intent(this, DiyEdit.class);
		Intent i = new Intent(this, AndroidTabAndListView.class);
		startActivityForResult(i, ACTIVITY_CREATE);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent i = new Intent(this, AndroidTabAndListView.class);
		i.putExtra(DiyDbAdapter.KEY_ROWID, id);

		startActivityForResult(i, ACTIVITY_EDIT);

		// TODO: fill in rest of method

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		switch (requestCode) {
		default:
			fillData();
			Intent i = new Intent();
			i.setAction("com.diyapp.kreator.UPDATE");
			sendBroadcast(i);
			break;
		}
	}

}
