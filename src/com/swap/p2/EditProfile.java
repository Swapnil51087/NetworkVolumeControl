package com.swap.p2;

import java.sql.SQLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditProfile extends Activity implements View.OnClickListener {

	// ---  Declare variables ---

	DBAdapter db = new DBAdapter(this);

	private Button save;
	private Button reset;
	private TextView profileName;
	private EditText wifiName;
	private RadioButton silent;
	private RadioButton vibrate;
	private RadioButton ring;
	private String message;
	private String initWifiName;

	// ---  Actions to be performed when this Activity is called ---
	protected void onCreate(Bundle savedInstanceState) {
		// ---  TODO Auto-generated method stub ---
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editxml);

		Intent intent = getIntent();
		
		// ---  get the profile name value which was clicked on home screen ---
		message = intent.getStringExtra(Home.EXTRA_MESSAGE); 

		save = (Button) findViewById(R.id.eSave);
		save.setOnClickListener(this);
		reset = (Button) findViewById(R.id.eReset);
		reset.setOnClickListener(this);
		profileName = (TextView) findViewById(R.id.etvprofilename);

		wifiName = (EditText) findViewById(R.id.ewifiname);
		silent = (RadioButton) findViewById(R.id.eSilent);
		ring = (RadioButton) findViewById(R.id.eRinging);
		vibrate = (RadioButton) findViewById(R.id.eVibrate);

		// ---  set the value of the Profile Name ---
		profileName.setText(message); 
		try {
			db.open();
		} catch (SQLException e) {
			// ---  TODO Auto-generated catch block ---
			e.printStackTrace();
		}
		
		// --- get all the values from the Database ---
		Cursor cursor = db.getAllRecords();
		if (cursor.moveToFirst()) {
			do {

				if (cursor.getString(0).equals(message)) {
					initWifiName = cursor.getString(2);
					wifiName.setText(cursor.getString(2));
					String type = cursor.getString(1);
					if (type.equalsIgnoreCase(silent.getText().toString()))
						silent.setChecked(true);
					else if (type.equalsIgnoreCase(ring.getText().toString()))
						ring.setChecked(true);
					else if (type
							.equalsIgnoreCase(vibrate.getText().toString()))
						vibrate.setChecked(true);
				}

			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

	}

	// ---  Actions to be performed on click events ---
	@Override
	public void onClick(View v) {
		// ---  TODO Auto-generated method stub ---
		int flag = 0;
		// ---  Switch Case to determine which button was clicked ---
		switch (v.getId()) {
		
		// ---  Case when save button was clicked. If all the values are entered then update the values in DB ---
		case R.id.eSave:
			if(wifiName.getText().toString().equalsIgnoreCase(initWifiName)){
				
				if (silent.isChecked()
						&& (profileName.getText().toString().length() != 0)
						&& (wifiName.getText().length() != 0)) {
					try {
						db.open();
					} catch (SQLException e) {
						// ---  TODO Auto-generated catch block ---
						e.printStackTrace();
					}
					
					
					
					db.updateRecord(profileName.getText().toString(), silent
							.getText().toString(), wifiName.getText().toString());
					db.close();

					Intent intent = new Intent(this, Home.class);
					startActivity(intent);
					
					
				}

				else if (ring.isChecked()
						&& (profileName.getText().toString().length() != 0)
						&& (wifiName.getText().length() != 0)) {
					try {
						db.open();
					} catch (SQLException e) {
						// ---  TODO Auto-generated catch block ---
						e.printStackTrace();
					}
					
					db.updateRecord(profileName.getText().toString(), ring
							.getText().toString(), wifiName.getText().toString());
					db.close();

					Intent intent = new Intent(this, Home.class);
					startActivity(intent);
					
				
				}

				else if (vibrate.isChecked()
						&& (profileName.getText().toString().length() != 0)
						&& (wifiName.getText().length() != 0)) {
					try {
						db.open();
					} catch (SQLException e) {
						// ---  TODO Auto-generated catch block ---
						e.printStackTrace();
					}
					
					db.updateRecord(profileName.getText().toString(), vibrate
							.getText().toString(), wifiName.getText().toString());
					db.close();

					Intent intent = new Intent(this, Home.class);
					startActivity(intent);
					
				} 
				
				else {
					alert();
				}
				
				break;
				
				
			}
			else{
			if (silent.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// ---  TODO Auto-generated catch block ---
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name already exists ---
						if(wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				
				if(flag == 0){
				db.updateRecord(profileName.getText().toString(), silent
						.getText().toString(), wifiName.getText().toString());
				db.close();

				Intent intent = new Intent(this, Home.class);
				startActivity(intent);
				}else 
					alertMessage();
				
			}

			else if (ring.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// ---  TODO Auto-generated catch block ---
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name already exists ---
						if(wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				
				if(flag == 0){
				db.updateRecord(profileName.getText().toString(), ring
						.getText().toString(), wifiName.getText().toString());
				db.close();

				Intent intent = new Intent(this, Home.class);
				startActivity(intent);
				}else 
					alertMessage();
			
			}

			else if (vibrate.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// ---  TODO Auto-generated catch block ---
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name already exists ---
						if(wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				
				if(flag == 0){
				db.updateRecord(profileName.getText().toString(), vibrate
						.getText().toString(), wifiName.getText().toString());
				db.close();

				Intent intent = new Intent(this, Home.class);
				startActivity(intent);
				} else 
					alertMessage();
			} 
			
			else {
				alert();
			}
			
			break;
		}	
		// ---  When Reset button is clicked Reset all the values to the empty except the profile Name ---.
		case R.id.eReset:
			wifiName.setText("");
			silent.setChecked(false);
			ring.setChecked(false);
			vibrate.setChecked(false);
			break;

		}
		
	}
	
	// ---  Alert Message displayed when save button is clicked without entering all the values ---.
	public void alert() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("Please enter all the values");
		builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ---  TODO Auto-generated method stub ---

			}
		});

		builder1.show();
	}
	
	// ---  Alert Message displayed when duplicate wi-fi name is entered ---
	public void alertMessage() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("The wi-fi name already exists, Please enter different value");
		builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		builder1.show();
	}

	
	// --- Actions Performed when this activity is on pause or closed ---
	protected void onPause() {
		// ---  TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	// --- Actions  to be performed on click of Back button ---
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent a = new Intent(this, Home.class);
			a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(a);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
