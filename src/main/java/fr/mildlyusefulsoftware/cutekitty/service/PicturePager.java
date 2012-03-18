package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicturePager {

	private List<Picture> pictures = new ArrayList<Picture>();
	private PictureProvider provider = null;
	private static PicturePager instance = null;
	private int lastPageLoaded = 0;

	public static PicturePager getInstance(Context context) {
		if (instance == null) {
			instance = new PicturePager(context);
		}
		return instance;
	}

	private PicturePager(Context context) {
		super();
		provider = new PictureProvider(context);
	}

	public Bitmap getPictureAt(int index) throws IOException {
		if (pictures.size() <= index) {
			loadPicturesUntil(index);
		}
		Picture p = pictures.get(index);
		Bitmap b = getBitmapFromPicture(p);
		return b;
	}

	private void loadPicturesUntil(int index) throws IOException {
		List<Picture> pictureToAdd=new ArrayList<Picture>();
		while (pictures.size() +pictureToAdd.size()<= index) {
			pictureToAdd.addAll(provider.getPicturePage(lastPageLoaded + 1).getPictures());
			lastPageLoaded++;
		}
		pictures.addAll(pictureToAdd);

	}

	private Bitmap getBitmapFromPicture(Picture picture) throws IOException {
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeByteArray(picture.getImage(), 0,
				picture.getImage().length, options);

		return bitmap;
	}
}
