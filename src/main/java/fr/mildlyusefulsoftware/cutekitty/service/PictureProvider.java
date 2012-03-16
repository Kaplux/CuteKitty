package fr.mildlyusefulsoftware.cutekitty.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PictureProvider {

	public byte[] getNextPicture(int currentPictureID) throws IOException {
		Document doc = getDocument("http://icanhascheezburger.com/page/"+(currentPictureID+1));
		return getPictureBytes(doc.select("img.event-item-lol-image").first()
				.attr("src"));
	}
	
	public byte[] getPreviousPicture(int currentPictureID) throws IOException {
		Document doc = getDocument("http://icanhascheezburger.com/page/"+(currentPictureID-1));
		return getPictureBytes(doc.select("img.event-item-lol-image").first()
				.attr("src"));
	}


	private Document getDocument(String url) throws IOException {
		return Jsoup.connect(url).data("query", "Java").userAgent("Mozilla")
				.cookie("auth", "token").timeout(3000).post();
	}

	private byte[] getPictureBytes(String url) throws IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			return IOUtils.toByteArray(in);
		} finally {
			in.close();
		}
	}

}
