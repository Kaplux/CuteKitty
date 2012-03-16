package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicturePager {

	private int currentPictureInPageIndex = 0;
	private PicturePage currentPage =null;
	private PictureProvider provider = null;
	private static PicturePager instance = null;

	public static PicturePager getInstance() {
		if (instance == null) {
			instance = new PicturePager();
		}
		return instance;
	}

	private PicturePager() {
		super();
		provider = new PictureProvider();
	}

	public Bitmap getOlderPicture() throws IOException {
		if (currentPage==null){
			currentPage=provider.getPicturePage(1);
		}
		
		if (currentPictureInPageIndex+1>=currentPage.getNumberOfPictures()){
			currentPage=provider.getPicturePage(currentPage.getPageNumber()+1);
			currentPictureInPageIndex=-1;
		}
		Bitmap b= getBitmapFromPicture(currentPage.getPictureAtIndex(currentPictureInPageIndex+1));
		currentPictureInPageIndex++;
		return b;
	}

	public Bitmap getNewerPicture() throws IOException {
		if (currentPage==null){
			currentPage=provider.getPicturePage(1);
		}
		
		if (currentPictureInPageIndex-1<0){
			currentPage=provider.getPicturePage(currentPage.getPageNumber()-1);
			currentPictureInPageIndex=currentPage.getNumberOfPictures();
		}
		
		Bitmap b= getBitmapFromPicture(currentPage.getPictureAtIndex(currentPictureInPageIndex-1));
		currentPictureInPageIndex--;
		return b;
	}

	private Bitmap getBitmapFromPicture(Picture picture) throws IOException {
		picture.setBytes(provider.getPictureBytes(picture.getImageURL()));
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		bitmap = BitmapFactory.decodeByteArray(picture.getBytes(), 0,
				picture.getBytes().length, options);

		return bitmap;
	}
}
