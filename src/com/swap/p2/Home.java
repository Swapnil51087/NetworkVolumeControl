package com.swap.p2;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Home extends Activity implements View.OnClickListener {

	// ---  Declare Variables --- 
	
	public final static String EXTRA_MESSAGE = "android.intent.action.MAIN";
	private Button add;
	private LinearLayout mContainerView;
	private DBAdapter db = new DBAdapter(this);
	private static Button delete;
	private static TextView tv;
	private static View rowView;
	private static View hideRow;
	private static TextView tv1;

	
	// --- Actions to be performed on creation of activity ---
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		startService(new Intent(getBaseContext(), BackgroundService.class));
		setContentView(R.layout.home_xml);
		add = (Button) findViewById(R.id.addprofile);
		add.setOnClickListener(this);

		mContainerView = (LinearLayout) findViewById(R.id.parentView);

		openDB();
		Cursor cursor = db.getAllRecords();
		DisplayRecords(cursor);
		db.close();
	}

	// --- open Database Connection
	private void openDB() {
		try {
			db.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// --- Actions to be performed on Click of Add profile button ---
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Home.this, CreateProfile.class);
		startActivity(intent);
	}

	public void DisplayRecords(Cursor c) {
		String message = "";
		if (c.moveToFirst()) {
			do {

				message = c.getString(0);
				displayText(message);

			} while (c.moveToNext());
		}
		c.close();

	}

	// --- Display the profile names on the Home Screen ---
	private void displayText(String string) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		rowView = inflater.inflate(R.layout.rowlayout, null);
		// --- find the text view used for the display of profile name --- 
		tv = (TextView) rowView.findViewById(R.id.tvprofile); 
		tv.setText(string);
		// --- find the text view used for the display of profile name ---
		delete = (Button) rowView.findViewById(R.id.bdelete); 
		rowView.setVisibility(View.VISIBLE);

		tv.setVisibility(View.VISIBLE);
		delete.setVisibility(View.VISIBLE);

		LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		hideRow = inflater1.inflate(R.layout.blankline, null);
		tv1 = (TextView) hideRow.findViewById(R.id.blanktext);
		tv1.setVisibility(View.VISIBLE);
		hideRow.setVisibility(View.VISIBLE);

		mContainerView.addView(rowView);
		mContainerView.addView(hideRow);

	}

	public void onDeleteClicked(View v) {
		String value;
		
		// --- To get the Parent ID of the button clicked to delete the profile ---
		RelativeLayout l1 = (RelativeLayout) v.getParent(); 
		
		// --- get the text view to find the profile name  ---
		TextView tvdelete = (TextView) l1.getChildAt(0); 
		value = tvdelete.getText().toString(); 

		openDB();
		// --- get all profile values in DB ---
		Cursor cursor = db.getAllRecords(); 

		if (cursor.moveToFirst()) {   
			do {

				if (value.equalsIgnoreCase(cursor.getString(0))) {
					// --- delete the record from the DB ---
					db.deleteRecord(value);  
					db.close();
					finish();
					startActivity(getIntent());
					break;
				}
			} while (cursor.moveToNext());
			db.close();
		}

	}

	// --- Method to To get the clicked profile value and Switch to Editing the profile ---
	public void onProfileClick(View v) {
		Intent intent = new Intent(this, EditProfile.class);
		String message;

		RelativeLayout l2 = (RelativeLayout) v;
		TextView tvPname = (TextView) l2.getChildAt(0);
		message = tvPname.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

	
	// --- Actions to be performed when this activity is paused or closed ---
	protected void onPause() {
		// --- TODO Auto-generated method stub ---
		super.onPause();
		finish();
	}

}
