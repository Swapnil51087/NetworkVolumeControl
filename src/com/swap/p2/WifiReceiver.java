package com.swap.p2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class WifiReceiver extends BroadcastReceiver {
	
	
	// --- Call BackgroundService class when Wi-Fi connection changes  --- 
    @Override
    public void onReceive(Context context, Intent intent) {     
    	
    	Intent serviceIntent = new Intent(context,BackgroundService.class);
        context.startService(serviceIntent);
    } 
    
    

}
