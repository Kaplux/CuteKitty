package fr.mildlyusefulsoftware.cutekitty.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PeriodicalUpdater extends BroadcastReceiver {
	private static String TAG = "cutekitty";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Intent newIntent = new Intent(context, IndexationService.class);
			newIntent.putExtra(IndexationService.START_PAGE_KEY, intent
					.getExtras().getInt(IndexationService.START_PAGE_KEY));
			newIntent.putExtra(
					IndexationService.NUMBER_OF_PAGES_KEY,
					intent.getExtras().getInt(
							IndexationService.NUMBER_OF_PAGES_KEY));
			context.startService(newIntent);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);

		}

	}
	
	public static void scheduleDatabaseUpdate(Context context,boolean replaceIfExists,boolean startNow) {
		Intent intent = getIndexerIntent(context,0, 5);
		if (replaceIfExists || PendingIntent.getBroadcast(context, 1, intent,
				PendingIntent.FLAG_NO_CREATE) == null) {
			PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			long updateInterval =AlarmManager.INTERVAL_HALF_DAY;
			long startTime=System.currentTimeMillis();
			if (!startNow){
				startTime+=updateInterval;
			}
			Log.d(TAG, "creating update service interval : "+updateInterval);
			am.setRepeating(AlarmManager.RTC, startTime,
					updateInterval, sender);
		}else{
			Log.d(TAG, "update service already set");
		}

	}
	
	private static Intent getIndexerIntent(Context context,int startPage, int numberOfPages) {
		Intent intent = new Intent(context,
				PeriodicalUpdater.class);
		intent.putExtra(IndexationService.START_PAGE_KEY, startPage);
		intent.putExtra(IndexationService.NUMBER_OF_PAGES_KEY,
				numberOfPages);
		return intent;
	}

}
