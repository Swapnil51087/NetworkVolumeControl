package com.swap.p2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedIntentReceiver extends BroadcastReceiver {
	@Override
	// --- On Boot completion the BackgroundService should start ---
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			
			Intent serviceIntent = new Intent(context, BackgroundService.class);
			context.startService(serviceIntent);
		}
	}
}