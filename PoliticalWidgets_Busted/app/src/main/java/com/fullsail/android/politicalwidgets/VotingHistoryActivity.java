package com.fullsail.android.politicalwidgets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.android.politicalwidgets.fragments.VoteHistoryFragment;
import com.fullsail.android.politicalwidgets.storage.Politician;

public class VotingHistoryActivity extends Activity {
	
	public static final String EXTRA_POLITICIAN = "VotingHistoryActivity.EXTRA_POLITICIAN";
	
	Politician mPolitician;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		if(!intent.hasExtra(EXTRA_POLITICIAN)) {
			throw new IllegalArgumentException("You must specify a politician to view the voting history.");
		}
		
		mPolitician = (Politician)intent.getSerializableExtra(EXTRA_POLITICIAN);

		if(getActionBar() != null) {
			getActionBar().setTitle(mPolitician.getName());
		}
		
		if(savedInstanceState == null) {
			VoteHistoryFragment frag = VoteHistoryFragment.newInstance(mPolitician);
			getFragmentManager().beginTransaction().replace(R.id.container, frag, VoteHistoryFragment.TAG).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_save) {
			Intent intent = new Intent(this, FavoriteSaverService.class);
			// TODO: What is being saved?
			startService(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}