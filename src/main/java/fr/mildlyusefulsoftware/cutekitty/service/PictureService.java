package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureService {

	private static final String TAG = "cutekitty";
	private PictureProvider provider;

	public PictureService() {
		super();
		provider = new PictureProvider();
	}

	public Bitmap getNextPicture(int currentPictureID) throws IOException {
		Bitmap bitmap = null;

		final byte[] data = provider.getNextPicture(currentPictureID);
		BitmapFactory.Options options = new BitmapFactory.Options();

		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

		return bitmap;
	}

	public Bitmap getPreviousPicture(int currentPictureID) throws IOException {
		Bitmap bitmap = null;

		final byte[] data = provider.getPreviousPicture(currentPictureID);
		BitmapFactory.Options options = new BitmapFactory.Options();

		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

		return bitmap;
	}

}
