package fr.mildlyusefulsoftware.cutekitty.activity;

import android.os.Bundle;
import fr.mildlyusefulsoftware.cutekitty.service.PeriodicalUpdater;



public class ViewPictureActivity extends fr.mildlyusefulsoftware.imageviewer.activity.ViewPictureActivity {

	
	
	@Override
	protected String getAdMobId() {
		return "a14f6f84d0cc485";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PeriodicalUpdater.scheduleDatabaseUpdate(this, true, true);
	}

	

}
