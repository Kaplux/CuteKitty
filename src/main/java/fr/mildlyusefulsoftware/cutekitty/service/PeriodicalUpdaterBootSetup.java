package fr.mildlyusefulsoftware.cutekitty.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PeriodicalUpdaterBootSetup extends BroadcastReceiver {
	private static String TAG = "cutekitty";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			PeriodicalUpdater.scheduleDatabaseUpdate(context,false,true);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);

		}

	}

	
	
}
