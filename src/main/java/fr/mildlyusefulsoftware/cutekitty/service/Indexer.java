package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import android.content.Context;
import android.util.Log;
import fr.mildlyusefulsoftware.imageviewer.service.DatabaseHelper;
import fr.mildlyusefulsoftware.imageviewer.service.Picture;

public class Indexer {

	private static String TAG = "cutekitty";
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Context context;

	public Indexer(Context context) {
		this.context = context;
	}

	public int index(final int startPage,
			final int numberOfPages) {
		Log.i(TAG,
				"indexing at "
						+ sdf.format(System.currentTimeMillis()));
		DatabaseHelper db = DatabaseHelper.connect(context);
		final List<Picture> loadedPictures= new ArrayList<Picture>();
		try {
			loadedPictures.addAll(db.getPictures());
		} finally {
			db.release();
		}
		Log.d(TAG, "start fetching " + sdf.format(System.currentTimeMillis()));
		int nbPicturesAdded=incrementallyFetchPictures(
				loadedPictures,startPage, numberOfPages);

		Log.i(TAG,
				"done quotes fetch at "
						+ sdf.format(System.currentTimeMillis()));
		return nbPicturesAdded;
	}
	

	/**
	 * Fetch quotes page after page and stop if a quote already exists in
	 * database
	 * 
	 * @param loadedQuotes
	 * @param p
	 * @return
	 */
	private int incrementallyFetchPictures(List<Picture> loadedPictures, int startPage, int numberOfPages) {
		int numberOfQuotesAdded = 0;
		boolean databaseAlreadyContainsPicture = false;
		PictureProvider pictureProvider=new PictureProvider(context);
		@SuppressWarnings("unchecked")
		Collection<String> urlsOfPictures = CollectionUtils.collect(
				loadedPictures, new Transformer() {

					@Override
					public Object transform(Object picture) {
						return ((Picture) picture).getImageURL();
					}
				});
		try {
			List<Picture> picturesToAdd = new ArrayList<Picture>();
			for (int i = startPage; i < numberOfPages
					&& !databaseAlreadyContainsPicture; i++) {
				List<Picture> potentialPicutresToAdd = pictureProvider.getPicturesFromPage(i);
				for (Picture p : potentialPicutresToAdd) {
					if (!pictureAlreadyInList(p, urlsOfPictures)) {
						picturesToAdd.add(p);
					}else{
						databaseAlreadyContainsPicture = true;
					}
				}
			}
			DatabaseHelper db = DatabaseHelper.connect(context);
			try {
				db.putPictures(picturesToAdd);
				numberOfQuotesAdded = picturesToAdd.size();
			} finally {
				db.release();
			}

		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return numberOfQuotesAdded;
	}

	private boolean pictureAlreadyInList(final Picture p,
			Collection<String> urlsOfPictures) {
		return (urlsOfPictures.contains(p.getImageURL()));
	}

}
