package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PictureProvider {

	private static String TAG = "cutekitty";

	private SQLiteDatabase bdd;

	public PictureProvider(Context context) {
		super();
		bdd = new PageCacheDatabaseHelper(context, "PAGES.db", null, 1)
				.getWritableDatabase();
	}

	private PicturePage getPageFromCache(int pageNumber) {
		PicturePage page = new PicturePage(pageNumber);
		Cursor c = bdd.query(PageCacheDatabaseHelper.TABLE_PAGES, new String[] {
				PageCacheDatabaseHelper.COL_ID,
				PageCacheDatabaseHelper.COL_PAGE_NUMBER,
				PageCacheDatabaseHelper.COL_IMAGE },
				PageCacheDatabaseHelper.COL_PAGE_NUMBER + " = " + pageNumber,
				null, null, null, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				Picture picture = new Picture(c.getInt(0), c.getInt(1),
						c.getBlob(2));
				page.addPicture(picture);
				c.moveToNext();
			}
		}
		return page;
	}

	public PicturePage getPicturePage(int pageNumber) throws IOException {
		Log.d(TAG, "loading page " + pageNumber);

		PicturePage page = getPageFromCache(pageNumber);
		if (page.getNumberOfPictures() == 0) {
			page = new PicturePage(pageNumber);
			Document doc = getDocument("http://icanhascheezburger.com/page/"
					+ pageNumber);
			for (Element elt : doc.select("div.post")) {
				Element imageElt = elt.select("img.event-item-lol-image")
						.first();
				if (imageElt != null) {
					int pictureId = Integer.valueOf(elt.attr("id").substring(
							elt.attr("id").lastIndexOf("-") + 1));
					String imageURL = imageElt.attr("src");
					page.addPicture(new Picture(pictureId, pageNumber,
							getPictureBytes(imageURL)));
				}
			}
			putPageInCache(page);
		}
		return page;
	}

	private void putPageInCache(PicturePage page) {
		for (Picture p:page.getPictures()){
			ContentValues values = new ContentValues();
			values.put(PageCacheDatabaseHelper.COL_PAGE_NUMBER, page.getPageNumber());
			values.put(PageCacheDatabaseHelper.COL_IMAGE, p.getImage());
			bdd.insert(PageCacheDatabaseHelper.TABLE_PAGES, null, values);
		}
		
	}

	private Document getDocument(String url) throws IOException {
		return Jsoup.connect(url).data("query", "Java").userAgent("Mozilla")
				.cookie("auth", "token").timeout(3000).post();
	}

	public byte[] getPictureBytes(String url) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			return IOUtils.toByteArray(in);
		} finally {
			in.close();
		}
	}

}
