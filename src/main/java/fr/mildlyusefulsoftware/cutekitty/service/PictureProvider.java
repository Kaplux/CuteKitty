package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

public class PictureProvider {

	private static String TAG = "cutekitty";

	public PicturePage getPicturePage(int pageNumber) throws IOException {
		Log.d(TAG,"loading page "+pageNumber);
		PicturePage page = new PicturePage(pageNumber);
		Document doc = getDocument("http://icanhascheezburger.com/page/"
				+ pageNumber);
		for (Element elt : doc.select("div.post")) {
			Element imageElt = elt.select("img.event-item-lol-image").first();
			if (imageElt != null) {
				int pictureId = Integer.valueOf(elt.attr("id").substring(
						elt.attr("id").lastIndexOf("-") + 1));
				String imageURL = imageElt.attr("src");
				page.addPicture(new Picture(pictureId, pageNumber, imageURL));
			}
		}
		return page;
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
