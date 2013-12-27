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

public class CreateProfile extends Activity implements View.OnClickListener {
	
	// --- Declare Variables ---
	private DBAdapter db = new DBAdapter(this);
	private Button save;
	private Button reset;
	private EditText profileName;
	private EditText wifiName;
	private RadioButton silent;
	private RadioButton vibrate;
	private RadioButton ring;


	// --- Actions to be performed on creation of activity ---
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub ---
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// --- Initialize Variables ---
		save = (Button) findViewById(R.id.Save);
		save.setOnClickListener(this);
		reset = (Button) findViewById(R.id.Reset);
		reset.setOnClickListener(this);
		profileName = (EditText) findViewById(R.id.edit_profilename);
		wifiName = (EditText) findViewById(R.id.edit_wifiname);
		silent = (RadioButton) findViewById(R.id.Silent);
		ring = (RadioButton) findViewById(R.id.Ringing);
		vibrate = (RadioButton) findViewById(R.id.Vibrate);

	}
	
	// --- Actions to be performed when Buttons are clicked --- 
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int flag = 0;
		switch (v.getId()) {
		
		//Insert records into the database once all the entries are complete
		case R.id.Save:
			if (silent.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name or profile Name already exists ---
						if(profileName.getText().toString().equalsIgnoreCase(c.getString(0)) 
								|| wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				if(flag == 0){
				db.insertRecord(profileName.getText().toString(), silent
						.getText().toString(), wifiName.getText().toString());
				db.close();
				flag=0;
				Intent intent = new Intent(CreateProfile.this, Home.class);
				startActivity(intent);
				}
				else 
					alertMessage();
			} 
			
			else if (ring.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name or profile Name already exists ---
						if(profileName.getText().toString().equalsIgnoreCase(c.getString(0)) 
								|| wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				if(flag == 0){
				db.insertRecord(profileName.getText().toString(), ring
						.getText().toString(), wifiName.getText().toString());
				db.close();
				flag=0;
				Intent intent = new Intent(CreateProfile.this, Home.class);
				startActivity(intent);
				}
				else 
					alertMessage();
			} 
			
			else if (vibrate.isChecked()
					&& (profileName.getText().toString().length() != 0)
					&& (wifiName.getText().length() != 0)) {
				try {
					db.open();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Cursor c = db.getAllRecords();
				if(c.moveToFirst()){
					do {
						// --- To check if wi-fi Name or profile Name already exists ---
						if(profileName.getText().toString().equalsIgnoreCase(c.getString(0)) 
								|| wifiName.getText().toString().equalsIgnoreCase(c.getString(2))){
							
							flag = 1;
						}

					} while (c.moveToNext());
				
				}
				if(flag == 0){
				db.insertRecord(profileName.getText().toString(), vibrate
						.getText().toString(), wifiName.getText().toString());
				db.close();
				flag=0;
				Intent intent = new Intent(CreateProfile.this, Home.class);
				startActivity(intent);
				}
				
				else 
					alertMessage();
			} 
			
			else {
				alert();
			}

			break;
		
			// --- Reset values of all the views to Null ---
		case R.id.Reset:
			profileName.setText("");
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
				// TODO Auto-generated method stub

			}
		});

		builder1.show();
	}
	
	// ---  Alert Message displayed when duplicate profile name or wi-fi name is entered ---
	public void alertMessage() {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("The profile name or wi-fi name already exists, Please enter different value");
		builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		builder1.show();
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
	
	// --- Actions Performed when this activity is on pause or closed ---
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}                                                  