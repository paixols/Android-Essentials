package com.fullsail.android.anayajuan_ce10.widgets.factory;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.VoteInfoActivity;
import com.fullsail.android.anayajuan_ce10.storage.VoteInfo;
import com.fullsail.android.anayajuan_ce10.utils.VoteInfoHelper;

public class VoteHistoryViewFactory implements RemoteViewsFactory {
	
	private static final int ID_CONSTANT = 0x030303030;
	
	private Context mContext;
	private int mWidgetId;
	private ArrayList<VoteInfo> mVotes;
	
	public VoteHistoryViewFactory(Context context, int widgetId) {
		mContext = context;
		mWidgetId = widgetId;
		mVotes = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mVotes.size();
	}

	@Override
	public long getItemId(int position) {
		return ID_CONSTANT + position;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		
		VoteInfo info = mVotes.get(position);
		
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.vote_info_list_item);
		rv.setTextViewText(R.id.question, info.getQuestion());
		rv.setTextViewText(R.id.vote, info.getVote());
		
		Intent intent = new Intent();
		intent.putExtra(VoteInfoActivity.EXTRA_VOTE_ID, info.getId());
		rv.setOnClickFillInIntent(R.id.root, intent);
		
		return rv;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;//Default Loading View
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

	}

	@Override
	public void onDataSetChanged() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		int politicianId = prefs.getInt("Widget" + mWidgetId, -1);

		if(politicianId == -1) {
			throw new IllegalArgumentException("Politician ID should not be -1. " +
					"Make sure preferences are saving properly.");
		}
		
		mVotes = VoteInfoHelper.getVoteHistory(politicianId);
	}

	@Override
	public void onDestroy() {
		mVotes.clear();
	}

}
