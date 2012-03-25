package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.util.Log;
import fr.mildlyusefulsoftware.cutekitty.service.DatabaseHelper;
import fr.mildlyusefulsoftware.cutekitty.service.Picture;
import fr.mildlyusefulsoftware.cutekitty.service.PictureProvider;

public class PicturePager {

	private static String TAG = "cutekitty";
	private static PicturePager instance;

	private List<Picture> pictures;

	private PicturePager(final Context context) {
		DatabaseHelper db = DatabaseHelper.connect(context);
		try {
			pictures = db.getPictures();
			if (pictures.isEmpty()){
				try {
					db.copyDatabase();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		} finally {
			db.release();
		}
//		if (pictures.isEmpty()) {
//			
//			ExecutorService taskExecutor = Executors.newFixedThreadPool(5);
//			List<Future> futures = new ArrayList<Future>();
//			for (int i = 0; i < 45; i++) {
//				final int currentPage = i;
//				futures.add(taskExecutor.submit(new Runnable() {
//
//					@Override
//					public void run() {
//						Log.d(TAG,"start fetching page "+currentPage);
//						PictureProvider provider = new PictureProvider(context);
//						try {
//							List<Picture> newPictures = provider
//									.getPicturesFromPage(currentPage);
//							DatabaseHelper db = DatabaseHelper.connect(context);
//							try {
//								db.putPictures(newPictures);
//							} catch (Exception e) {
//								Log.e(TAG, e.getMessage(), e);
//							}
//
//							finally {
//								db.release();
//							}
//						} catch (IOException e) {
//							Log.e(TAG, e.getMessage(), e);
//						}
//						Log.d(TAG,"ended fetching page "+currentPage);
//					}
//				}));
//
//			}
//			for (Future f : futures) {
//				try {
//					f.get();
//				} catch (InterruptedException e) {
//					Log.e(TAG, e.getMessage(), e);
//				} catch (ExecutionException e) {
//					Log.e(TAG, e.getMessage(), e);
//				}
//			}
//		}

		db = DatabaseHelper.connect(context);
		try {
			pictures = db.getPictures();
		} finally {
			db.release();
		}

	}

	public static PicturePager getInstance(Context context) {
		if (instance == null) {
			instance = new PicturePager(context);
		}
		return instance;
	}

	public Picture getPictureAt(int i) {
		return pictures.get(i);
	}

	public int getNumberOfPictures() {
		return pictures.size();
	}

}
