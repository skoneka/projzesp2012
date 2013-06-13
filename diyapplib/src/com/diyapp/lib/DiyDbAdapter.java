package com.diyapp.lib;

/* to insert new columns use insert_DiyDbAdapter.py script */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple diys database access helper class. Defines the basic CRUD operations
 * for the diy example, and gives the ability to list all diys as well as
 * retrieve or modify a specific diy.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class DiyDbAdapter {
	// increase version after modifying columns, clean and rebuild library AND project!
	private static final int DATABASE_VERSION = 23;

	private static final String DATABASE_NAME = "data2";
	private static final String DATABASE_TABLE = "diys";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_ENABLED = "enabled";
	// TEMPLATE_edit: public static final String KEY_{uppercase} = "{lowercase}";//

	// triggers
	public static final String KEY_TRIGGER_LOCATION = "trigger_location";
	public static final String KEY_TRIGGER_LOCATION_PARAM_LATITUDE = "trigger_location_param_latitude";
	public static final String KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE = "trigger_location_param_longtitude";
	public static final String KEY_TRIGGER_LOCATION_PARAM_AREA = "trigger_location_param_area";

	public static final String KEY_TRIGGER_DATE = "trigger_date";//
	public static final String KEY_TRIGGER_DATE_PARAM_FROM = "trigger_date_param_from";//
	public static final String KEY_TRIGGER_DATE_PARAM_TO = "trigger_date_param_to";//
	public static final String KEY_TRIGGER_WIFI = "trigger_wifi";//
	public static final String KEY_TRIGGER_WIFI_PARAM_SSID = "trigger_wifi_param_ssid";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY1 = "trigger_date_param_day1";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY2 = "trigger_date_param_day2";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY3 = "trigger_date_param_day3";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY4 = "trigger_date_param_day4";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY5 = "trigger_date_param_day5";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY6 = "trigger_date_param_day6";//
	public static final String KEY_TRIGGER_DATE_PARAM_DAY7 = "trigger_date_param_day7";//
	// TEMPLATE_triggers: public static final String KEY_{uppercase} = "{lowercase}";//

	// actions
	public static final String KEY_ACTION_WIFI = "action_wifi";//
	public static final String KEY_ACTION_WIFI_PARAM_TURN_ON = "action_wifi_param_turn_on";//
	public static final String KEY_ACTION_WIFI_PARAM_TURN_OFF = "action_wifi_param_turn_off";//
	public static final String KEY_ACTION_WIFI_PARAM_SSID = "action_wifi_param_ssid";//
	public static final String KEY_ACTION_NOTIFICATION = "action_notification";//
	public static final String KEY_ACTION_NOTIFICATION_PARAM_TEXT = "action_notification_param_text";//
	public static final String KEY_ACTION_NOTIFICATION_PARAM_TITLE = "action_notification_param_title";//
	public static final String KEY_ACTION_WIDGETTEXT = "action_widgettext";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_TEXT = "action_widgettext_param_text";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_DATE = "action_widgettext_param_display_date";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_COORDINATES = "action_widgettext_param_display_coordinates";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ADDRESS = "action_widgettext_param_display_address";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_WIFISSID = "action_widgettext_param_display_wifissid";//
	public static final String KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ACTION_DESCRIPTION = "action_widgettext_param_display_action_description";//
	public static final String KEY_ACTION_SOUNDPROFILE = "action_soundprofile";//
	public static final String KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND = "action_soundprofile_param_profile_sound";//
	public static final String KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS = "action_soundprofile_param_profile_vibrations";//
	public static final String KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME = "action_soundprofile_param_volume";//
	public static final String KEY_ACTION_NOTIFICATION_PARAM_WWW_TEXT = "action_notification_param_www_text";//
	public static final String KEY_ACTION_NOTIFICATION_PARAM_WWW_SWITCH = "action_notification_param_www_switch";//
	// TEMPLATE_actions: public static final String KEY_{uppercase} = "{lowercase}";//


	public static final String[] COLUMNS = new String[] { KEY_ROWID, //
			KEY_TITLE, //
			KEY_DESCRIPTION, //
			KEY_ENABLED, //
			// TEMPLATE_edit: KEY_{uppercase},//

			// triggers
			KEY_TRIGGER_LOCATION, //
			KEY_TRIGGER_LOCATION_PARAM_LATITUDE, //
			KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE, //
			KEY_TRIGGER_LOCATION_PARAM_AREA, //

			KEY_TRIGGER_DATE,//
			KEY_TRIGGER_DATE_PARAM_FROM,//
			KEY_TRIGGER_DATE_PARAM_TO,//
			KEY_TRIGGER_WIFI,//
			KEY_TRIGGER_WIFI_PARAM_SSID,//
			KEY_TRIGGER_DATE_PARAM_DAY1,//
			KEY_TRIGGER_DATE_PARAM_DAY2,//
			KEY_TRIGGER_DATE_PARAM_DAY3,//
			KEY_TRIGGER_DATE_PARAM_DAY4,//
			KEY_TRIGGER_DATE_PARAM_DAY5,//
			KEY_TRIGGER_DATE_PARAM_DAY6,//
			KEY_TRIGGER_DATE_PARAM_DAY7,//
			// TEMPLATE_triggers: KEY_{uppercase},//

			// actions
			KEY_ACTION_NOTIFICATION_PARAM_WWW_TEXT,// 
			KEY_ACTION_NOTIFICATION_PARAM_WWW_SWITCH,// 
			// TEMPLATE_actions: KEY_{uppercase},// 
			KEY_ACTION_WIFI,//
			KEY_ACTION_WIFI_PARAM_TURN_ON,//
			KEY_ACTION_WIFI_PARAM_TURN_OFF,//
			KEY_ACTION_WIFI_PARAM_SSID,//
			KEY_ACTION_NOTIFICATION,//
			KEY_ACTION_NOTIFICATION_PARAM_TEXT,//
			KEY_ACTION_NOTIFICATION_PARAM_TITLE,//
			KEY_ACTION_WIDGETTEXT,//
			KEY_ACTION_WIDGETTEXT_PARAM_TEXT,//
			KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_DATE,//
			KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_COORDINATES,//
			KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ADDRESS,//
			KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_WIFISSID,//
			KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ACTION_DESCRIPTION,//
			KEY_ACTION_SOUNDPROFILE,//
			KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND,//
			KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS,//
			KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME//

			}; //

	private static final String TAG = "DiyDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "create table " //
			+ DATABASE_TABLE + " ( " //
			+ KEY_ROWID + " integer primary key autoincrement, " //
			+ KEY_TITLE + " text not null, " //
			+ KEY_DESCRIPTION + " text not null, " //
			+ KEY_ENABLED + " integer not null, " // 0|1 is this diya active ?
			// TEMPLATE_edit: + KEY_{uppercase} + " {dbtype} not null,"//

			// triggers
			+ KEY_TRIGGER_LOCATION + " integer not null, " //
			+ KEY_TRIGGER_LOCATION_PARAM_LATITUDE + " real not null, " //
			+ KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE + " real not null, " //
			+ KEY_TRIGGER_LOCATION_PARAM_AREA + " real not null, " //

			+ KEY_TRIGGER_DATE + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_FROM + " text not null,"//
			+ KEY_TRIGGER_DATE_PARAM_TO + " text not null,"//
			+ KEY_TRIGGER_WIFI + " integer not null,"//
			+ KEY_TRIGGER_WIFI_PARAM_SSID + " text not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY1 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY2 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY3 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY4 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY5 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY6 + " integer not null,"//
			+ KEY_TRIGGER_DATE_PARAM_DAY7 + " integer not null,"//
			// TEMPLATE_triggers: + KEY_{uppercase} + " {dbtype} not null,"//

			// actions
			+ KEY_ACTION_NOTIFICATION_PARAM_WWW_TEXT + " text not null,"//
			+ KEY_ACTION_NOTIFICATION_PARAM_WWW_SWITCH + " integer not null,"//
			// TEMPLATE_actions: + KEY_{uppercase} + " {dbtype} not null,"//
			+ KEY_ACTION_WIFI + " integer not null,"//
			+ KEY_ACTION_WIFI_PARAM_TURN_ON + " integer not null,"//
			+ KEY_ACTION_WIFI_PARAM_TURN_OFF + " integer not null,"//
			+ KEY_ACTION_WIFI_PARAM_SSID + " text not null,"//
			+ KEY_ACTION_NOTIFICATION + " integer not null,"//
			+ KEY_ACTION_NOTIFICATION_PARAM_TEXT + " text not null,"//
			+ KEY_ACTION_NOTIFICATION_PARAM_TITLE + " text not null,"//
			+ KEY_ACTION_WIDGETTEXT + " integer not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_TEXT + " text not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_DATE + " integer not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_COORDINATES + " integer not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ADDRESS + " integer not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_WIFISSID + " integer not null,"//
			+ KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ACTION_DESCRIPTION + " integer not null,"//
			+ KEY_ACTION_SOUNDPROFILE + " integer not null,"//
			+ KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND + " integer not null,"//
			+ KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS + " integer not null,"//
			+ KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME + " integer not null);";//

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.v("DiyDbAdapter", "db: " + DATABASE_NAME + " version: "
					+ DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public DiyDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the diys database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public DiyDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Create a new diy using the title and description provided. If the diy is
	 * successfully created return the new rowId for that diy, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the diy
	 * @param description
	 *            the description of the diy
	 * @return rowId or -1 if failed
	 */
	public long createDiy(String title, String description, boolean enabled) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_DESCRIPTION, description);
		initialValues.put(KEY_ENABLED, enabled ? 1 : 0);
		// TEMPLATE_edit: initialValues.put(KEY_{uppercase}, {default_value});//

		// triggers
		initialValues.put(KEY_TRIGGER_LOCATION, 0);
		initialValues.put(KEY_TRIGGER_LOCATION_PARAM_LATITUDE, 0.0);
		initialValues.put(KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE, 0.0);
		initialValues.put(KEY_TRIGGER_LOCATION_PARAM_AREA, 100.0);
		initialValues.put(KEY_TRIGGER_DATE, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_FROM, "");//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_TO, "");//
		initialValues.put(KEY_TRIGGER_WIFI, 0);//
		initialValues.put(KEY_TRIGGER_WIFI_PARAM_SSID, "");//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY1, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY2, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY3, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY4, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY5, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY6, 0);//
		initialValues.put(KEY_TRIGGER_DATE_PARAM_DAY7, 0);//
		// TEMPLATE_triggers: initialValues.put(KEY_{uppercase}, {default_value});//

		// actions
		initialValues.put(KEY_ACTION_WIFI, 0);//
		initialValues.put(KEY_ACTION_WIFI_PARAM_TURN_ON, 0);//
		initialValues.put(KEY_ACTION_WIFI_PARAM_TURN_OFF, 0);//
		initialValues.put(KEY_ACTION_WIFI_PARAM_SSID, "");//
		initialValues.put(KEY_ACTION_NOTIFICATION, 0);//
		initialValues.put(KEY_ACTION_NOTIFICATION_PARAM_TEXT, "");//
		initialValues.put(KEY_ACTION_NOTIFICATION_PARAM_TITLE, "");//
		initialValues.put(KEY_ACTION_WIDGETTEXT, 0);//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_TEXT, "");//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_DATE, 1);//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_COORDINATES, 1);//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ADDRESS, 1);//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_WIFISSID, 1);//
		initialValues.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ACTION_DESCRIPTION, 1);//
		initialValues.put(KEY_ACTION_SOUNDPROFILE, 0);//
		initialValues.put(KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND, 0);//
		initialValues.put(KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS, 0);//
		initialValues.put(KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME, 7);//
		initialValues.put(KEY_ACTION_NOTIFICATION_PARAM_WWW_TEXT, "");//
		initialValues.put(KEY_ACTION_NOTIFICATION_PARAM_WWW_SWITCH, 0);//
		// TEMPLATE_actions: initialValues.put(KEY_{uppercase}, {default_value});//

		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Delete the diy with the given rowId
	 * 
	 * @param rowId
	 *            id of diy to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deleteDiy(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all diys in the database
	 * 
	 * @return Cursor over all diys
	 */
	public Cursor fetchAllDiy() {

		return mDb.query(DATABASE_TABLE, COLUMNS, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the diy that matches the given rowId
	 * 
	 * @param rowId
	 *            id of diy to retrieve
	 * @return Cursor positioned to matching diy, if found
	 * @throws SQLException
	 *             if diy could not be found/retrieved
	 */
	public Cursor fetchDiy(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE, COLUMNS, KEY_ROWID + "=" + rowId, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	/**
	 * Update the diy using the details provided. The diy to be updated is
	 * specified using the rowId, and it is altered to use the title and
	 * description values passed in
	 * 
	 * @param rowId
	 *            id of diy to update
	 * @param title
	 *            value to set diy title to
	 * @param description
	 *            value to set diy description to
	 * @return true if the diy was successfully updated, false otherwise
	 */
	public boolean updateDiy(long rowId, String title, String description,
			boolean enabled) {
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_DESCRIPTION, description);
		args.put(KEY_ENABLED, enabled ? 1 : 0);
		// TEMPLATE_edit: args.put(KEY_{uppercase}, {lowercase});

		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean updateDiyTriggers(long rowId,
			boolean trigger_date_param_day1,//
			boolean trigger_date_param_day2,//
			boolean trigger_date_param_day3,//
			boolean trigger_date_param_day4,//
			boolean trigger_date_param_day5,//
			boolean trigger_date_param_day6,//
			boolean trigger_date_param_day7,//
			// TEMPLATE_triggers: {vartype} {lowercase},//
			boolean trigger_date,
			String trigger_date_param_from,
			String trigger_date_param_to,
			boolean trigger_wifi,//
			String trigger_wifi_param_ssid,//
			boolean trigger_location_enabled,
			double trigger_location_param_latitude,
			double trigger_location_param_longtitude,
			double trigger_location_param_area) {
		ContentValues args = new ContentValues();
		args.put(KEY_TRIGGER_LOCATION, trigger_location_enabled ? 1 : 0);
		args.put(KEY_TRIGGER_LOCATION_PARAM_LATITUDE,
				trigger_location_param_latitude);
		args.put(KEY_TRIGGER_LOCATION_PARAM_LONGTITUDE, trigger_location_param_longtitude);
		args.put(KEY_TRIGGER_LOCATION_PARAM_AREA, trigger_location_param_area);
		args.put(KEY_TRIGGER_DATE, trigger_date  ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_FROM, trigger_date_param_from);
		args.put(KEY_TRIGGER_DATE_PARAM_TO, trigger_date_param_to);
		args.put(KEY_TRIGGER_WIFI, trigger_wifi);
		args.put(KEY_TRIGGER_WIFI_PARAM_SSID, trigger_wifi_param_ssid);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY1, trigger_date_param_day1 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY2, trigger_date_param_day2 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY3, trigger_date_param_day3 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY4, trigger_date_param_day4 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY5, trigger_date_param_day5 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY6, trigger_date_param_day6 ? 1 : 0);
		args.put(KEY_TRIGGER_DATE_PARAM_DAY7, trigger_date_param_day7 ? 1 : 0);
		// TEMPLATE_triggers: args.put(KEY_{uppercase}, {lowercase}{cmp});
		// must add '? 1 : 0' manually

		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public boolean updateDiyActions(
			long rowId,
			String action_notification_param_www_text,//
			boolean action_notification_param_www_switch,//
			// TEMPLATE_actions: {vartype} {lowercase},//
			boolean action_wifi,
			boolean action_wifi_param_turn_on,
			boolean action_wifi_param_turn_off,
			String action_wifi_param_ssid,
			boolean action_notification,//
			String action_notification_param_text,//
			String action_notification_param_title,//
			boolean action_widgettext,//
			String action_widgettext_param_text,//
			boolean action_widgettext_param_display_date,//
			boolean action_widgettext_param_display_coordinates,//
			boolean action_widgettext_param_display_address,//
			boolean action_widgettext_param_display_wifissid,//
			boolean action_widgettext_param_display_action_description,//
			boolean action_soundprofile,//
			boolean action_soundprofile_param_profile_sound,//
			boolean action_soundprofile_param_profile_vibrations,//
			int action_soundprofile_param_volume//
			) {
		ContentValues args = new ContentValues();
		args.put(KEY_ACTION_WIFI, action_wifi ? 1 : 0);
		args.put(KEY_ACTION_WIFI_PARAM_TURN_ON, action_wifi_param_turn_on ? 1 : 0);
		args.put(KEY_ACTION_WIFI_PARAM_TURN_OFF, action_wifi_param_turn_off ? 1 : 0);
		args.put(KEY_ACTION_WIFI_PARAM_SSID, action_wifi_param_ssid);
		args.put(KEY_ACTION_NOTIFICATION, action_notification ? 1 : 0);
		args.put(KEY_ACTION_NOTIFICATION_PARAM_TEXT, action_notification_param_text);
		args.put(KEY_ACTION_NOTIFICATION_PARAM_TITLE, action_notification_param_title);
		args.put(KEY_ACTION_WIDGETTEXT, action_widgettext ? 1 : 0);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_TEXT, action_widgettext_param_text);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_DATE, action_widgettext_param_display_date ? 1 : 0);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_COORDINATES, action_widgettext_param_display_coordinates ? 1 : 0);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ADDRESS, action_widgettext_param_display_address ? 1 : 0);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_WIFISSID, action_widgettext_param_display_wifissid ? 1 : 0);
		args.put(KEY_ACTION_WIDGETTEXT_PARAM_DISPLAY_ACTION_DESCRIPTION, action_widgettext_param_display_action_description ? 1 : 0);
		args.put(KEY_ACTION_SOUNDPROFILE, action_soundprofile ? 1 : 0);
		args.put(KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND, action_soundprofile_param_profile_sound ? 1 : 0);
		args.put(KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS, action_soundprofile_param_profile_vibrations ? 1 : 0);
		args.put(KEY_ACTION_SOUNDPROFILE_PARAM_VOLUME, action_soundprofile_param_volume);
		args.put(KEY_ACTION_NOTIFICATION_PARAM_WWW_TEXT, action_notification_param_www_text);
		args.put(KEY_ACTION_NOTIFICATION_PARAM_WWW_SWITCH, action_notification_param_www_switch ? 1 : 0);
		// TEMPLATE_actions: args.put(KEY_{uppercase}, {lowercase}{cmp});

		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}