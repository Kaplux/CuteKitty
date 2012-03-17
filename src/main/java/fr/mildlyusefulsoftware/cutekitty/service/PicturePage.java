package fr.mildlyusefulsoftware.cutekitty.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class PicturePage {

	List<Picture> pictures = new ArrayList<Picture>();

	private int pageNumber;

	public boolean containsPicture(final int pictureID){
		return CollectionUtils.exists(pictures, new Predicate() {
			
			@Override
			public boolean evaluate(Object picture) {
				return pictureID==((Picture)picture).getId();
			}
		});
	}


	public PicturePage(int pageNumber) {
		super();
		this.pageNumber = pageNumber;
	}

	public void addPicture(Picture p){
		pictures.add(p);
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Picture getPictureAtIndex(final int index) {
		return pictures.get(index);
	}


	public int getNumberOfPictures() {
		return pictures.size();
	}


	public List<Picture> getPictures() {
		return pictures;
	}


}
