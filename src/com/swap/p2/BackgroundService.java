package com.swap.p2;

import java.sql.SQLException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundService extends Service {

	// ---Declare Variables ---
	String ssid = null;
	AudioManager audioManager;
	WifiManager wifiManager;
	WifiInfo wifiInfo;
	android.net.wifi.SupplicantState s;
	NetworkInfo.DetailedState state;
	DBAdapter db = new DBAdapter(this);
	private final IBinder mBinder = new LocalBinder();
	
	public class LocalBinder extends Binder {
		BackgroundService getService() {
			return BackgroundService.this;
		}
	}
	
	// Perform actions on start of the Service
	public int onStartCommand(Intent intent, int flags, int startId) {
		audioManager = (AudioManager) this
				.getSystemService(Context.AUDIO_SERVICE);

		wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		openDB();
		// --- On Start check whether cell phone is connected to one of the profile values in the database ---
		checkProfile();
		return START_STICKY;

	}
	
	// --- Open Database ---
	private void openDB() {
		try {
			db.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public IBinder onBind(Intent intent) {

		return mBinder;
	}

	// --- Method to check whether cell phone is connected to one of the profile values in the database ---
	public void checkProfile() {

		Cursor c = db.getAllRecords();

		String name = wifiInfo.getSSID();

		System.out.println(wifiInfo.getSSID());
		if (c.moveToFirst()) {
			do {
				if (name.equalsIgnoreCase("\"" + c.getString(2) + "\"")) {
					if (c.getString(1).equals("Silent")) {
						audioManager
								.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						break;
					}

					if (c.getString(1).equals("Vibrate")) {
						audioManager
								.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
						break;
					}
					if (c.getString(1).equals("Ringing")) {
						audioManager
								.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
						break;
					}
				}

			} while (c.moveToNext());

		}
	}
}
