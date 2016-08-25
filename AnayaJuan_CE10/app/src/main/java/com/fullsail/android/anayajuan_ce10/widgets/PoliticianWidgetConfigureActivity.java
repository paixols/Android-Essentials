package com.fullsail.android.anayajuan_ce10.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.fragments.SettingsFragment;

public class PoliticianWidgetConfigureActivity extends Activity {

	private int mWidgetId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent launcherIntent = getIntent();
		Bundle extras = launcherIntent.getExtras();
		
		mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		
		if(extras != null) {
			mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		if(mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			setResult(RESULT_CANCELED);
			finish();
		}
		
		if(savedInstanceState == null) {
			SettingsFragment frag = SettingsFragment.newInstance(mWidgetId);
			getFragmentManager().beginTransaction().replace(R.id.container, frag, SettingsFragment.TAG).commit();
		}
	}
}