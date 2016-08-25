package com.fullsail.android.politicalwidgets.widgets.factory;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.fullsail.android.politicalwidgets.R;
import com.fullsail.android.politicalwidgets.VotingHistoryActivity;
import com.fullsail.android.politicalwidgets.fragments.PoliticiansListFragment;
import com.fullsail.android.politicalwidgets.storage.Politician;
import com.fullsail.android.politicalwidgets.utils.PoliticiansHelper;

public class PoliticianViewFactory implements RemoteViewsFactory {

	private static final int ID_CONSTANT = 0x040404040;
	
	private Context mContext;
	private int mWidgetId;
	private ArrayList<Politician> mPoliticians;
	
	public PoliticianViewFactory(Context context, int widgetId) {
		mContext = context;
		mWidgetId = widgetId;
		mPoliticians = new ArrayList<>();
	}

	@Override
	public long getItemId(int position) {
		return ID_CONSTANT + position;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		
		Politician politician = mPoliticians.get(position);
		
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.politicians_list_item);
		rv.setTextViewText(R.id.name, politician.getName());
		rv.setTextViewText(R.id.party, politician.getParty());
		rv.setTextViewText(R.id.description, politician.getDescription());
		
		Intent intent = new Intent();
		intent.putExtra(VotingHistoryActivity.EXTRA_POLITICIAN, politician);
		rv.setOnClickFillInIntent(R.id.root, intent);
		
		return null;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;//Default View
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		int filter = prefs.getInt("Filter" + mWidgetId, -1);
		
		if(filter == PoliticiansListFragment.FILTER_ALL) {
			mPoliticians = PoliticiansHelper.getAllPoliticians();
		} else if(filter == PoliticiansListFragment.FILTER_FAVORITES) {
			mPoliticians = PoliticiansHelper.getFavoritePoliticians(mContext);
		}
	}

	@Override
	public void onDataSetChanged() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		int filter = prefs.getInt("Filter" + mWidgetId, -1);
		
		if(filter == PoliticiansListFragment.FILTER_ALL) {
			mPoliticians = PoliticiansHelper.getAllPoliticians();
		} else if(filter == PoliticiansListFragment.FILTER_FAVORITES) {
			mPoliticians = PoliticiansHelper.getFavoritePoliticians(mContext);
		}
	}

	@Override
	public void onDestroy() {
		mPoliticians.clear();
	}

	@Override
	public int getCount() {
		return 0;//TODO Change the count
	}

}
