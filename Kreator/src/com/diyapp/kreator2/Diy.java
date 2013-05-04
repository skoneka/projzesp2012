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

import com.diyapp.lib.DiyDbAdapter;

public class Diy extends ListActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int MAP_ID = Menu.FIRST + 2;

    private DiyDbAdapter mDbHelper;
    
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
        	Log.v("diy",string);
        }
    	Log.v("diy","kreator");

    }
    
    private void fillData() {
        // Get all of the rows from the database and create the item list
    	Cursor diysCursor = mDbHelper.fetchAllDiy();
        startManagingCursor(diysCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DiyDbAdapter.KEY_TITLE};
        
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter diys = 
        	    new SimpleCursorAdapter(this, R.layout.diys_row, diysCursor, from, to);
        setListAdapter(diys);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID,0, R.string.menu_insert);
        menu.add(0, MAP_ID,0, "Map");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case INSERT_ID:
            createDiy();
            return true;
        case MAP_ID:
        	Intent i = new Intent(this, DiyMapActivity.class);
        	startActivity(i);
        	break;
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
    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    		mDbHelper.deleteDiy(info.id);
    		fillData();
    		return true;
    	}
		return super.onContextItemSelected(item);
		
        // TODO: fill in rest of method
	}

    private void createDiy() {
        // TODO: fill in implementation
    	//Intent i = new Intent(this, DiyEdit.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
		Intent i = new Intent();
		i.setAction("com.diyapp.kreator.UPDATE");
		sendBroadcast(i);
    }

}