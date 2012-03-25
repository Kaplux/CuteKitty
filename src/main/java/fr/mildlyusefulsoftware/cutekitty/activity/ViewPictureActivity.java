package fr.mildlyusefulsoftware.cutekitty.activity;

import java.io.IOException;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import fr.mildlyusefulsoftware.cutekitty.R;
import fr.mildlyusefulsoftware.cutekitty.service.Picture;

public class ViewPictureActivity extends Activity {

	private static String TAG = "cutekitty";
	private AdView adView;

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
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.view_picture_layout);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icon);
		initAdBannerView();
		Gallery pictureList = (Gallery) findViewById(R.id.pictureList);
		Bitmap b;
		try {
			b = Picture.getBitmapFromPicture(PicturePager.getInstance(this).getPictureAt(0));
			ImageView pictureView=(ImageView) findViewById(R.id.pictureView);
			pictureView.setImageBitmap(b);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pictureList.setAdapter(new ImageAdapter(this));
		pictureList.setOnItemClickListener(new OnItemClickListener() {
	        @Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	try {
					Bitmap b=Picture.getBitmapFromPicture(PicturePager.getInstance(getApplicationContext()).getPictureAt(position));
					ImageView pictureView=(ImageView) findViewById(R.id.pictureView);
					pictureView.setImageBitmap(b);
					Toast.makeText(getApplicationContext(),"ID : "+PicturePager.getInstance(getApplicationContext()).getPictureAt(position).getId(), Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
		}

	
	protected void initAdBannerView() {
		final ViewGroup quoteLayout = (ViewGroup) findViewById(R.id.view_picutre_root_layout);
		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "a14f6e53e04f4bf");
		LinearLayout adLayout = new LinearLayout(this);
		adLayout.addView(adLayout);
		// Add the adView to it
		quoteLayout.addView(adView);
		AdRequest ar=new AdRequest();
		ar.addTestDevice(AdRequest.TEST_EMULATOR);
		// Initiate a generic request to load it with an ad
		adView.loadAd(ar);

	}
	

	@Override
	protected void onDestroy() {
		super.onPause();
		if (adView != null) {
			adView.destroy();
		}
	}
	
}
