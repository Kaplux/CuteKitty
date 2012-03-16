package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.IOException;

import android.graphics.Bitmap;

public class PicturePager {

	private int currentPage = -1;
	private PictureService pictureService = null;
	private static PicturePager instance = null;

	public static PicturePager getInstance() {
		if (instance == null) {
			instance = new PicturePager();
		}
		return instance;
	}

	private PicturePager() {
		super();
		pictureService = new PictureService();
	}

	public Bitmap getNextPicture() throws IOException {
		Bitmap picture = pictureService.getNextPicture(currentPage);
		currentPage++;
		return picture;
	}

	public Bitmap getPreviousPicture() throws IOException {
		if (currentPage > 0) {
			Bitmap picture = pictureService.getPreviousPicture(currentPage);
			currentPage--;
			return picture;
		} else {
			return null;
		}
	}
}
