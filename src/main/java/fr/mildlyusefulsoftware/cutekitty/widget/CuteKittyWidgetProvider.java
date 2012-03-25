package fr.mildlyusefulsoftware.cutekitty.widget;

import java.io.IOException;
import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import fr.mildlyusefulsoftware.cutekitty.R;
import fr.mildlyusefulsoftware.cutekitty.activity.ViewPictureActivity;
import fr.mildlyusefulsoftware.cutekitty.service.DatabaseHelper;
import fr.mildlyusefulsoftware.cutekitty.service.Picture;

public class CuteKittyWidgetProvider extends AppWidgetProvider {

	private static String TAG = "cutekitty";

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			Log.d(TAG, "widget update");
			int appWidgetId = appWidgetIds[i];
			Intent intent = new Intent(context, ViewPictureActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			views.setOnClickPendingIntent(R.id.pictureView, pendingIntent);
			DatabaseHelper db = DatabaseHelper.connect(context);
			try {
				List<Picture> pictures = db.getPictures();
				Picture p = pictures
						.get((int) (Math.random() * pictures.size()));

				views.setImageViewBitmap(R.id.widgetPictureView,
						Picture.getBitmapFromPicture(p));

				appWidgetManager.updateAppWidget(appWidgetId, views);
			} catch (IOException e) {
				Log.e(TAG, e.getMessage(), e);
			} finally {
				db.release();
			}
		}
	}
}
