package com.fullsail.android.anayajuan_ce10.widgets;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.VoteInfoActivity;
import com.fullsail.android.anayajuan_ce10.fragments.PoliticiansListFragment;
import com.fullsail.android.anayajuan_ce10.fragments.PoliticiansListFragment.PoliticianSelector;
import com.fullsail.android.anayajuan_ce10.storage.Politician;

public class VoteHistoryWidgetConfigureActivity extends Activity implements PoliticianSelector {
    //TAG
    private static final String TAG = "VoteHistWidgConfigAct";

    private int mWidgetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent launcherIntent = getIntent();
		Bundle extras = launcherIntent.getExtras();

		mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        
        //Dev
        Log.i(TAG, "onCreate: " + "WIDGET ID: " + mWidgetId);

		if(extras != null) {
			mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		
		if(mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			setResult(RESULT_CANCELED);
			finish();
		}
		
		if(savedInstanceState == null) {
			PoliticiansListFragment frag = PoliticiansListFragment.newInstance(PoliticiansListFragment.FILTER_ALL);
			getFragmentManager().beginTransaction().replace(R.id.container, frag, PoliticiansListFragment.TAG).commit();
		}
	}

	@Override
	public void politicianSelected(Politician p) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.edit().putInt("Widget" + mWidgetId, p.getId()).commit();//apply()
		
		Intent intent = new Intent(this, ListWidgetService.class);
	    intent.setData(Uri.fromParts("content", String.valueOf(mWidgetId), null)); // Leave this line alone.
		intent.putExtra(ListWidgetService.EXTRA_TYPE, ListWidgetService.TYPE_VOTE_HISTORY);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mWidgetId);
		
		RemoteViews rv = new RemoteViews(getApplicationContext().getPackageName(), R.layout.list_widget_layout);
		rv.setRemoteAdapter(R.id.list, intent);
		rv.setEmptyView(R.id.list, R.id.empty);
		
		Intent voteInfo = new Intent(this, VoteInfoActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, voteInfo, PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setPendingIntentTemplate(R.id.list, pIntent);
		
		AppWidgetManager mgr = AppWidgetManager.getInstance(this);
		mgr.updateAppWidget(mWidgetId, rv);
		
		Intent result = new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
		setResult(RESULT_OK, result);
		finish();
	}
}
