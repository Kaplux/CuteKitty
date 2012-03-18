package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import fr.mildlyusefulsoftware.cutekitty.service.PicturePager;

public class ImageAdapter extends BaseAdapter implements SpinnerAdapter {

    int mGalleryItemBackground;
    private Context mContext;
   
    public ImageAdapter(Context c) {
        mContext = c;
      
    
    }

    public int getCount() {
        return 100;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
       
        try {
        	Bitmap b=PicturePager.getInstance(mContext).getPictureAt(position);
        	imageView.setImageBitmap(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }

}
