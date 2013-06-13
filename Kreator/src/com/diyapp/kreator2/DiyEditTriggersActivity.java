package com.diyapp.kreator2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.diyapp.kreator2.R;
import com.diyapp.lib.DiyDbAdapter;

public class DiyEditTriggersActivity extends Activity {
	private static final int ACTIVITY_MAP = 2;
	private static final int ACTIVITY_DATE_PICKER_FROM = 3;
	private static final int ACTIVITY_DATE_PICKER_TO = 4;
	
	ToggleButton mtrigger_wifi_enabled;
	TextView mtrigger_wifi_param_ssid;
	
	ToggleButton mtrigger_date_enabled;
	TextView mtrigger_date_param_from;
	TextView mtrigger_date_param_to;

	ToggleButton mtrigger_location_enabled;
	static TextView mtrigger_location_param_latitude;
	static TextView mtrigger_location_param_longtitude;
	EditText mtrigger_location_param_area;
	CheckBox mtrigger_date_param_day1;//
	CheckBox mtrigger_date_param_day2;//
	CheckBox mtrigger_date_param_day3;//
	CheckBox mtrigger_date_param_day4;//
	CheckBox mtrigger_date_param_day5;//
	CheckBox mtrigger_date_param_day6;//
	CheckBox mtrigger_date_param_day7;//
	// TEMPLATE: {field} m{lowercase};//

	static double var_latitude = -1;
	static double var_longtitude = -1;
	String from_timedate = "";
	String to_timedate = "";

	Long mRowId;

	private DiyDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mDbHelper = new DiyDbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.activity_diy_edit_triggers);
		setTitle(R.string.edit_diy);

		// triggers
		mtrigger_wifi_enabled = (ToggleButton) findViewById(R.id.trigger_wifi_enabled);
		mtrigger_wifi_param_ssid = (EditText) findViewById(R.id.trigger_wifi_param_ssid);
		
		mtrigger_date_enabled = (ToggleButton) findViewById(R.id.trigger_date_enabled);
		mtrigger_date_param_from = (TextView) findViewById(R.id.trigger_date_param_from);
		mtrigger_date_param_to = (TextView) findViewById(R.id.trigger_date_param_to);
		

		mtrigger_location_enabled = (ToggleButton) findViewById(R.id.trigger_location_enabled);
		mtrigger_location_param_latitude = (TextView) findViewById(R.id.trigger_location_param_latitude);
		mtrigger_location_param_longtitude = (TextView) findViewById(R.id.trigger_location_param_longtitude);
		mtrigger_location_param_area = (EditText) findViewById(R.id.trigger_location_param_area);
		mtrigger_date_param_day1 = (CheckBox) findViewById(R.id.trigger_date_param_day1);//
		mtrigger_date_param_day2 = (CheckBox) findViewById(R.id.trigger_date_param_day2);//
		mtrigger_date_param_day3 = (CheckBox) findViewById(R.id.trigger_date_param_day3);//
		mtrigger_date_param_day4 = (CheckBox) findViewById(R.id.trigger_date_param_day4);//
		mtrigger_date_param_day5 = (CheckBox) findViewById(R.id.trigger_date_param_day5);//
		mtrigger_date_param_day6 = (CheckBox) findViewById(R.id.trigger_date_param_day6);//
		mtrigger_date_param_day7 = (CheckBox) findViewById(R.id.trigger_date_param_day7);//
		// TEMPLATE: m{lowercase} = ({field}) findViewById(R.id.{lowercase});//
		
		// disable
//		mtrigger_location_param_latitude
//				.setVisibility(mtrigger_location_param_latitude.INVISIBLE);
//		mtrigger_location_param_longtitude
//				.setVisibility(mtrigger_location_param_longtitude.INVISIBLE);

		populateFields();

		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(DiyDbAdapter.KEY_ROWID);

		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(DiyDbAdapter.KEY_ROWID)
					: null;
		}

//		Toast toast = Toast.makeText(this, "var_latitude " + var_latitude
//				+ " var_latitude " + var_latitude, Toast.LENGTH_SHORT);
//		toast.show();
		
		Log.v("DiyEditTriggersActivity onCreate", "var_latitude " + var_latitude
				+ " var_latitude " + var_latitude);
		
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor diy = mDbHelper.fetchDiy(mRowId);
			startManagingCursor(diy);

			// triggers
			mtrigger_wifi_enabled.setChecked(1 == diy.getInt(diy
					.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI)));
			mtrigger_wifi_param_ssid
					.setText(diy.getString(diy
							.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI_PARAM_SSID)));
			

			mtrigger_location_enabled.setChecked(1 == diy.getInt(diy
					.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION)));
			if ( var_latitude == -1 ) {
				var_latitude = diy
						.getDouble(diy
								.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LATITUDE));
				var_longtitude = diy
						.getDouble(diy
								.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE));
//				Toast toast = Toast.makeText(this, "populate var_latitude " + var_latitude
//						+ " var_latitude " + var_latitude, Toast.LENGTH_SHORT);
//				toast.show();
			}
			
			// unable to update theses 2
			mtrigger_location_param_latitude
					.setText(Double.toString(diy.getDouble(diy
							.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LATITUDE))));
			mtrigger_location_param_longtitude
					.setText(Double.toString(diy.getDouble(diy
							.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE))));
			// --
			
			 //this things are not updating because we are not in a view!
			 mtrigger_location_param_latitude.setText(Double
			 .toString(var_latitude));
			 DiyEditTriggersActivity.this.mtrigger_location_param_longtitude
			 .setText(Double .toString(var_longtitude));
			 

			mtrigger_location_param_area
					.setText(Double.toString(diy.getDouble(diy
							.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION_PARAM_AREA))));
			
			if ( from_timedate == "" ) {
				from_timedate = diy.getString(diy
						.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_FROM));
			}
			if ( to_timedate == "" ) {
				to_timedate = diy.getString(diy
						.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_TO));
			}
			
			mtrigger_date_enabled.setChecked(1 == diy.getInt(diy
					.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE)));
			mtrigger_date_param_from.setText(from_timedate);
			mtrigger_date_param_to.setText(to_timedate);
			mtrigger_date_param_day1.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY1)));//
			mtrigger_date_param_day2.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY2)));//
			mtrigger_date_param_day3.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY3)));//
			mtrigger_date_param_day4.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY4)));//
			mtrigger_date_param_day5.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY5)));//
			mtrigger_date_param_day6.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY6)));//
			mtrigger_date_param_day7.setChecked( 1 == diy.getInt(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE_PARAM_DAY7)));//
			// TEMPLATE: m{lowercase}.{diyget}(diy.getColumnIndexOrThrow(DiyDbAdapter.KEY_{uppercase})));//
			

			
			

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(DiyDbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		mDbHelper.updateDiyTriggers(
				mRowId, //
				// triggers
				mtrigger_date_param_day1.isChecked(),//
				mtrigger_date_param_day2.isChecked(),//
				mtrigger_date_param_day3.isChecked(),//
				mtrigger_date_param_day4.isChecked(),//
				mtrigger_date_param_day5.isChecked(),//
				mtrigger_date_param_day6.isChecked(),//
				mtrigger_date_param_day7.isChecked(),//
				// TEMPLATE: m{lowercase}.{retrieve}(),//
				mtrigger_date_enabled.isChecked(),
				mtrigger_date_param_from.getText().toString(),
				mtrigger_date_param_to.getText().toString(),
				mtrigger_wifi_enabled.isChecked(), //
				mtrigger_wifi_param_ssid.getText().toString(),
				mtrigger_location_enabled.isChecked(), //
				var_latitude, //
				var_longtitude, //
				/*
				 * Double.parseDouble(mtrigger_location_param_latitude.getText()
				 * .toString()), //
				 * Double.parseDouble(mtrigger_location_param_longtitude
				 * .getText() .toString()), //
				 */
				Double.parseDouble(mtrigger_location_param_area.getText()
						.toString()) //
				); //
	}

	public void showMap(View v) {
		Intent intent = new Intent(this, DiyMapActivity.class);
		intent.putExtra("latitude", var_latitude);
		intent.putExtra("longtitude", var_longtitude);
		startActivityForResult(intent, ACTIVITY_MAP);
	}
	
	public void showDatePickerFrom(View v) {
		Intent intent = new Intent(this, DiyDatePicker.class);
		startActivityForResult(intent, ACTIVITY_DATE_PICKER_FROM);
	}
	
	public void showDatePickerTo(View v) {
		Intent intent = new Intent(this, DiyDatePicker.class);
		startActivityForResult(intent, ACTIVITY_DATE_PICKER_TO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		switch (requestCode) {
		case ACTIVITY_MAP:
			if (resultCode == RESULT_OK) {
				double latitude = intent.getDoubleExtra("latitude", 0.0);
				double longtitude = intent.getDoubleExtra("longtitude", 0.0);
				Log.v("diy", "latitude = " + Double.toString(latitude)
						+ " longtitude= " + Double.toString(longtitude));

				var_latitude = latitude;
				var_longtitude = longtitude;


			}
			break;
		case ACTIVITY_DATE_PICKER_FROM:
			if (resultCode == RESULT_OK) {
				from_timedate = intent.getStringExtra("timedate");
				Log.v("diy", "from_timedate = " + from_timedate);
			}
			break;		
		case ACTIVITY_DATE_PICKER_TO:
			if (resultCode == RESULT_OK) {
				to_timedate = intent.getStringExtra("timedate");
				Log.v("diy", "to_timedate = " + to_timedate);
			}
			break;	
		default:
			break;
		}
	}

}
