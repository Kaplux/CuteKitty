package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import fr.mildlyusefulsoftware.cutekitty.service.DatabaseHelper;
import fr.mildlyusefulsoftware.cutekitty.service.Picture;
import fr.mildlyusefulsoftware.cutekitty.service.PictureProvider;

public class PicturePager {


	private static String TAG = "cutekitty";
	private static PicturePager instance;
	
	private List<Picture> pictures;
	
	
	private PicturePager(Context context) {
		DatabaseHelper db=DatabaseHelper.connect(context);
		try{
			pictures=db.getPictures();
			if (pictures.isEmpty()){
				PictureProvider provider=new PictureProvider(context);
				pictures=provider.getPicturesFromPage(0);
				db.putPictures(pictures);
			}
		} catch (IOException e) {
			Log.e(TAG,e.getMessage(),e);
		}finally{
			db.release();
		}
		
	}
	
	public static PicturePager getInstance(Context context){
		if (instance==null){
			instance=new PicturePager(context);
		}
		return instance;
	}

	public Picture getPictureAt(int i) {
		return pictures.get(i);
	}
	
	public int getNumberOfPictures() {
		return pictures.size();
	}
	
	
	
}
