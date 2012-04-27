package fr.mildlyusefulsoftware.cutekitty.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import fr.mildlyusefulsoftware.cutekitty.R;
import fr.mildlyusefulsoftware.cutekitty.activity.ViewPictureActivity;

public class IndexationService extends IntentService {

	public IndexationService() {
		super("IndexationService");
	}

	private static String TAG = "cutekittty";

	public static final String START_PAGE_KEY = "START_PAGE";

	public static final String NUMBER_OF_PAGES_KEY = "NUMBER_OF_PAGES";

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Starting indexing service");
		int startPage = intent.getExtras().getInt(START_PAGE_KEY);
		int numberOfPages = intent.getExtras().getInt(NUMBER_OF_PAGES_KEY);

		int nbPicturesAdded = new Indexer(getApplicationContext()).index(startPage, numberOfPages);
		if (nbPicturesAdded>0){
			sendNotification(nbPicturesAdded);
		}
		Log.i(TAG, "Indexing service ended.");
	}

	private void sendNotification(int nbPicturesAdded) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.icon;
		CharSequence tickerText = "New pictures available. " + nbPicturesAdded
				+ " pictures added.";
		long when = System.currentTimeMillis();
		CharSequence contentTitle = "Picture database updated";
		CharSequence contentText = nbPicturesAdded
				+ " picture added. Touch to open Cute Kitty";

		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		Intent notificationIntent = new Intent(Intent.ACTION_MAIN);
		notificationIntent.setClass(getApplicationContext(),
				ViewPictureActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(getApplicationContext(), contentTitle,
				contentText, contentIntent);

		mNotificationManager.notify(1, notification);
	}

}
