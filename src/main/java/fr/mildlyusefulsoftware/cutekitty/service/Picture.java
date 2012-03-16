package fr.mildlyusefulsoftware.cutekitty.service;

public class Picture {

	private byte[] bytes;

	private int id;

	private int pageNumber;

	private String imageURL;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Picture(int id, int pageNumber, String imageURL) {
		super();
		this.imageURL = imageURL;
		this.id = id;
		this.pageNumber = pageNumber;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
