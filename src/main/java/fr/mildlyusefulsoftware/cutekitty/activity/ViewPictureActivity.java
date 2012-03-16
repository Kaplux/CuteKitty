package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import fr.mildlyusefulsoftware.cutekitty.R;
import fr.mildlyusefulsoftware.cutekitty.service.PicturePager;

public class ViewPictureActivity extends Activity {

	private static String TAG = "cutekitty";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.view_picture_layout);
		ImageView pictureView = (ImageView) findViewById(R.id.pictureView);
		pictureView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				gdt.onTouchEvent(event);
				return true;
			}
		});
		try {
			loadOlderPicture();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	private void loadOlderPicture() throws IOException {
		final ProgressDialog progressDialog = ProgressDialog.show(this,
				"Loading", "please wait", true);
		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {

				try {
					return PicturePager.getInstance().getOlderPicture();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return null;
			}

			protected void onPreExecute() {
				super.onPreExecute();
			}

			protected void onPostExecute(Bitmap picture) {
				progressDialog.dismiss();
				ImageView pictureView = (ImageView) findViewById(R.id.pictureView);
				if (picture != null) {
					pictureView.setImageBitmap(picture);
				}
			}

		}.execute();

	}

	private void loadNewerPicture() throws IOException {
		final ProgressDialog progressDialog = ProgressDialog.show(this,
				"Loading", "please wait", true);
		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {

				try {
					return PicturePager.getInstance().getNewerPicture();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return null;
			}

			protected void onPreExecute() {
				super.onPreExecute();
			}

			protected void onPostExecute(Bitmap picture) {
				progressDialog.dismiss();
				ImageView pictureView = (ImageView) findViewById(R.id.pictureView);
				if (picture != null) {
					pictureView.setImageBitmap(picture);
				}
			}

		}.execute();
	}

	private final GestureDetector gdt = new GestureDetector(
			new GestureListener());

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.d(TAG, "loading older picture");
				try {
					loadOlderPicture();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return false; // Right to left
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.d(TAG, "loading newer picture");
				try {
					loadNewerPicture();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
				return false; // Left to right
			}

			return false;
		}
	}
}
