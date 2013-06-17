package pl.diya.execute2;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.diyapp.lib.DiyDbAdapter;

import pl.diya.execute2.IRemoteService.Stub;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.diyapp.lib.DiyDbAdapter;

public class Execute extends Service {

	Actions ac = new Actions(this);
	Triggers tr = new Triggers(this);

	static DiyDbAdapter mDbHelper;

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mDbHelper.setServiceStatus(false);
	}
	
	
	// implementation of the aidl interface
	private final IRemoteService.Stub mBinder = new Stub() {

		@Override
		public String sayHello(String message) throws RemoteException {
			//android.os.Debug.waitForDebugger();

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
			
			tr.setDiyDbAdapter(mDbHelper);
			ac.setDiyDbAdapter(mDbHelper);
			
			okresowewykonywanie();
			
			//mDbHelper.close();
			return "Hello " + message;

		}

	};
	private boolean  []wykonalem = new boolean[100];
	private void okresowewykonywanie() {
		
		//Intent intent = PendingIntent.getActivity(getInstance().getBaseContext(), 0,
	    //        new Intent(Execute.getIntent()), getIntent().getFlags());
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			public void run() {
				
				int idWarunku = 0;
				int idWiersza;
				int warunek = 0;
				boolean trigger = true;
				Cursor c = mDbHelper.fetchAllDiy();
				
				if (c.moveToFirst()) {
					do {
						trigger = true;
						idWiersza = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ROWID));
						//System.out.println("Jestem w pêtli, w wierszu "+idWiersza);
						if((c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ENABLED))==1)){
							
						

						System.out.println("DIYa aktywna "+idWiersza);
						
					
									warunek = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_DATE));
								if(warunek == 1)
								{	
									System.out.println("Jestem w sprawdzaniu triggera");
									if(!tr.dzienIGodzina(idWiersza))
										trigger = false;
								}
							

							//System.out.println("Jestem za pierwszym ifem, zwróci³: "+warunek+" a trigger "+trigger);
							
							//System.out.println("Jestem za pierwszym ifem, zwróci³: "+warunek+" a trigger "+trigger);
							warunek = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_LOCATION));	
							if(warunek == 1)
								{
									if(!tr.czyWDanymMiejscu(idWiersza))
										trigger = false;
								}
						
							//System.out.println("Jestem za drugim ifem, zwróci³: "+warunek);
							
							warunek = c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_TRIGGER_WIFI));	
							if(warunek == 1)
								{
									if(!tr.czyWifiWlaczone(idWiersza))
										trigger = false;
								}
							if(!trigger && wykonalem[idWiersza])
								wykonalem[idWiersza] = false;
							//System.out.println("Jestem za trzecim ifem, zwróci³: "+warunek);
							//System.out.println("Trigger " + trigger);
							
							if(trigger && !wykonalem[idWiersza]){
								//if(trigger){
								wykonalem[idWiersza] = true;
								//System.out.println("ala0.7");
								if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_WIFI)) == 1)
								{
									//System.out.println("ala1");
									if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_WIFI_PARAM_TURN_ON)) == 1)
									{
										//System.out.println("ala2");
										if(!c.getString(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_WIFI_PARAM_SSID)).equals("")){
											//System.out.println("ala3");
											ac.podlaczWifiDoDanejSieci(idWiersza);
										}
										else{
											//System.out.println("ala4");
											ac.wlaczWifi(idWiersza);
										}
										
									}
									else if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_WIFI_PARAM_TURN_OFF)) == 1)
									{
										//System.out.println("ala5");
										ac.wylaczWifi(idWiersza);
									}
								}
								//System.out.println("ala5.7");
								if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_NOTIFICATION)) == 1){
									//System.out.println("ala6");
									ac.wyswietlPowiadomienie(idWiersza);
								}
								//System.out.println("ala6.7");
								if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_SOUNDPROFILE)) == 1)
								{
									//System.out.println("ala7");
									if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_SOUND)) == 1)
									{
										//System.out.println("ala8");
										ac.glosnoscDzwiek(idWiersza);
									}
									else if(c.getInt(c.getColumnIndexOrThrow(DiyDbAdapter.KEY_ACTION_SOUNDPROFILE_PARAM_PROFILE_VIBRATIONS)) == 1)
									{
										//System.out.println("ala9");
										ac.glosnoscWibracje(idWiersza);
									}
								}
								
								//System.out.println("ala10");
							}
							//System.out.println("ala11");
							//System.out.println("Jestem za trzecim ifem, zwróci³: "+warunek);
							//System.out.println("Koñcowy wynik: " + trigger);
							
						}
					} while (c.moveToNext());
				}
				
				//System.out.println("ala12");
				
			}
		};

		timer.scheduleAtFixedRate(timerTask, 100, 10000);
	}

}
