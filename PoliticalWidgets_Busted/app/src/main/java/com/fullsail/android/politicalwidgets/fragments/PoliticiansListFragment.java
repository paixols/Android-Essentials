package com.fullsail.android.politicalwidgets.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fullsail.android.politicalwidgets.R;
import com.fullsail.android.politicalwidgets.VotingHistoryActivity;
import com.fullsail.android.politicalwidgets.storage.Politician;
import com.fullsail.android.politicalwidgets.utils.PoliticiansHelper;

public class PoliticiansListFragment extends ListFragment {

	public static final String ARG_FILTER = "PoliticiansListFragment.ARG_FILTER";
	
	public static final int FILTER_ALL = 0x01001;
	public static final int FILTER_FAVORITES = 0x01002;
	
	private static final int REQUEST_VOTING_HISTORY = 0x02001;
	
	public static PoliticiansListFragment newInstance(int filter) {
		PoliticiansListFragment frag = new PoliticiansListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_FILTER, filter);
		frag.setArguments(args);
		return frag;
	}
	
	private int mFilter;
	private PoliticiansLoaderTask mTask;
	private PoliticianSelector mSelector;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(activity instanceof PoliticianSelector) {
			mSelector = (PoliticianSelector)activity;
		} else {
			mSelector = null;
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle args = getArguments();
		if(args == null || args.isEmpty()) {
			throw new IllegalArgumentException("Must pass in a filter for viewing politicians.");
		}
		
		if(args.containsKey(ARG_FILTER)) {
			mFilter = args.getInt(ARG_FILTER);
			if(mFilter != FILTER_ALL && mFilter != FILTER_FAVORITES) {
				throw new IllegalArgumentException("Must pass in a valid filter for viewing politicians.");
			}
		}
		
		if(mFilter == FILTER_FAVORITES) {
			setEmptyText(getString(R.string.no_favorites));
		}
		
		loadPoliticians();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		PoliticiansAdapter adapter = (PoliticiansAdapter)l.getAdapter();
		Politician p = adapter.getItem(position);
		
		if(mSelector == null) {		
			throw new IllegalStateException("mSelector should not be null. Make sure the " +
					"containing activity properly implements the PoliticianSelector interface.");
		} else {
			mSelector.politicianSelected(p);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		loadPoliticians();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopTask();
	}
	
	private void loadPoliticians() {
		stopTask();
		mTask = new PoliticiansLoaderTask(getActivity());
		mTask.execute(mFilter);
	}
	
	private void stopTask() {
		if(mTask != null) {
			mTask.cancel(false);
			mTask = null;
		}
	}
	
	public void refresh() {
		loadPoliticians();
	}
	
	private class PoliticiansAdapter extends BaseAdapter {
		
		private static final int ID_CONSTANT = 0x0101010;
		
		private Context mContext;
		private ArrayList<Politician> mPoliticians;
			
		public PoliticiansAdapter(Context context, ArrayList<Politician> politicians) {
			mContext = context;
			mPoliticians = politicians;
		}

		@Override
		public int getCount() {
			return mPoliticians.size();
		}

		@Override
		public Politician getItem(int position) {
			return mPoliticians.get(position);
		}

		@Override
		public long getItemId(int position) {
			return ID_CONSTANT + position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.politicians_list_item, parent, false);
			}
			
			Politician politician = getItem(position);
			
			TextView tv = (TextView)convertView.findViewById(R.id.name);
			tv.setText(politician.getName());
			
			tv = (TextView)convertView.findViewById(R.id.party);
			tv.setText(politician.getParty());
			
			tv = (TextView)convertView.findViewById(R.id.description);
			tv.setText(politician.getDescription());
			
			return convertView;
		}
		
	}
	
	private class PoliticiansLoaderTask extends AsyncTask<Integer, Void, ArrayList<Politician>> {
		
		private Context mContext;
		
		public PoliticiansLoaderTask(Context context) {
			mContext = context;
		}

		@Override
		protected ArrayList<Politician> doInBackground(Integer... params) {
			
			int filter = params[0];

			if(filter != FILTER_ALL && filter != FILTER_FAVORITES) {
				throw new IllegalArgumentException("You must pass a valid filter into " +
						"the execute method of this task.");
			}
			
			ArrayList<Politician> politicians = null;
			
			if(filter == FILTER_ALL) {
				politicians = PoliticiansHelper.getAllPoliticians();
			} else if(filter == FILTER_FAVORITES) {
				politicians = PoliticiansHelper.getFavoritePoliticians(mContext);
			}
			
			return politicians;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Politician> result) {
			super.onPostExecute(result);
			
			mTask = null;
			
			PoliticiansAdapter adapter = new PoliticiansAdapter(getActivity(), result);
			setListAdapter(adapter);
		}
	}
	
	public interface PoliticianSelector {
		public void politicianSelected(Politician p);
	}
}