package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import fr.mildlyusefulsoftware.cutekitty.R;
import fr.mildlyusefulsoftware.cutekitty.service.PicturePager;

public class ViewPictureActivity extends Activity {

	private static String TAG = "cutekitty";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.view_picture_layout);
		Gallery pictureList = (Gallery) findViewById(R.id.pictureList);
		Bitmap b;
		try {
			b = PicturePager.getInstance().getPictureAt(0);
			ImageView pictureView=(ImageView) findViewById(R.id.pictureView);
			pictureView.setImageBitmap(b);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pictureList.setAdapter(new ImageAdapter(this));
		pictureList.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	try {
					Bitmap b=PicturePager.getInstance().getPictureAt(position);
					ImageView pictureView=(ImageView) findViewById(R.id.pictureView);
					pictureView.setImageBitmap(b);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
		}

	
}
