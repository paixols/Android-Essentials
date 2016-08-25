package com.fullsail.android.politicalwidgets.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.fullsail.android.politicalwidgets.R;
import com.fullsail.android.politicalwidgets.VotingHistoryActivity;
import com.fullsail.android.politicalwidgets.widgets.ListWidgetService;

public class SettingsFragment extends Fragment implements OnClickListener {
	
	public static final String TAG = "SettingsFragment.TAG";
	public static final String ARG_WIDGET_ID = "SettingsFragment.ARG_WIDGET_ID";
	
	public static SettingsFragment newInstance(int widgetId) {
		SettingsFragment frag = new SettingsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_WIDGET_ID, widgetId);
		frag.setArguments(args);
		return frag;
	}
	
	private int mWidgetId;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.settings_fragment, container, false);
		v.findViewById(R.id.all).setOnClickListener(this);
		v.findViewById(R.id.favorites).setOnClickListener(this);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle args = getArguments();
		if(args == null || !args.containsKey(ARG_WIDGET_ID)) {
			throw new IllegalArgumentException("You must supply a widget ID to configure.");
		}
		
		mWidgetId = args.getInt(ARG_WIDGET_ID);
	}
	
	@Override
	public void onClick(View v) {

		// TODO: Ensure proper update ID and result.
		
		int filter = -1;
		if(v.getId() == R.id.all) {
			filter = PoliticiansListFragment.FILTER_ALL;
		} else {
			filter = PoliticiansListFragment.FILTER_FAVORITES;
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		prefs.edit().putInt("Filter" + mWidgetId, filter).commit();
		
		Intent intent = new Intent(getActivity(), ListWidgetService.class);
	    intent.setData(Uri.fromParts("content", String.valueOf(mWidgetId), null)); // Leave this line alone.
		
		RemoteViews rv = new RemoteViews(getActivity().getPackageName(), R.layout.list_widget_layout);
		rv.setRemoteAdapter(R.id.empty, intent);
		rv.setEmptyView(R.id.list, R.id.empty);
		
		Intent historyInfo = new Intent(getActivity(), VotingHistoryActivity.class);
		PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), 0, historyInfo, PendingIntent.FLAG_UPDATE_CURRENT);
		rv.setPendingIntentTemplate(R.id.list, pIntent);
		
		AppWidgetManager mgr = AppWidgetManager.getInstance(getActivity());
		mgr.updateAppWidget(-1, rv);
		
		Intent result = new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
		getActivity().setResult(Activity.RESULT_CANCELED, result);
		getActivity().finish();
	}
}