package fr.mildlyusefulsoftware.cutekitty.service;

public class Picture {

	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	private int id;

	private int pageNumber;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Picture(int id, int pageNumber, byte[] image) {
		super();
		this.image = image;
		this.id = id;
		this.pageNumber = pageNumber;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
