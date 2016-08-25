package com.fullsail.android.politicalwidgets;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.fullsail.android.politicalwidgets.fragments.PoliticiansListFragment;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

	public static final String ACTION_SAVE_COMPLETE = "com.fullsail.android.politicalwidgets.ACTION_SAVE_COMPLETE";
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		actionBar.setListNavigationCallbacks(
				new ArrayAdapter<>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
							getString(R.string.all),
							getString(R.string.favorites) }),
						this);

		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		PoliticiansListFragment frag = null;
		if(position == 0) {
			frag = PoliticiansListFragment.newInstance(PoliticiansListFragment.FILTER_ALL);
		} else {
			frag = PoliticiansListFragment.newInstance(PoliticiansListFragment.FILTER_FAVORITES);
		}
		getFragmentManager().beginTransaction().replace(R.id.container, frag, PoliticiansListFragment.TAG).commit();
		return true;
	}
	
	protected void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SAVE_COMPLETE);
		registerReceiver(mReceiver, filter);
	}
	
	protected void onPause() {
		super.onPause();
		
		unregisterReceiver(mReceiver);
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(ACTION_SAVE_COMPLETE)) {
				PoliticiansListFragment frag = (PoliticiansListFragment)getFragmentManager()
						.findFragmentByTag(PoliticiansListFragment.TAG);
				frag.refresh();
			}
		}
	};
}
