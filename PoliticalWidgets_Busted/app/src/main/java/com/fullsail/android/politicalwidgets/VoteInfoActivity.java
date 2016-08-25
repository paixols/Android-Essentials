package com.fullsail.android.politicalwidgets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fullsail.android.politicalwidgets.fragments.VoteInfoFragment;

public class VoteInfoActivity extends Activity {
	
	public static final String EXTRA_VOTE_ID = "VoteInfoActivity.EXTRA_VOTE_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		if(!intent.hasExtra(EXTRA_VOTE_ID)) {
			throw new IllegalArgumentException("You must supply a vote ID to view the details.");
		}
		
		int voteId = intent.getIntExtra(EXTRA_VOTE_ID, -1);

		if(voteId == -1) {
			throw new IllegalArgumentException("You must supply a VALID vote ID to view the details.");
		}
		
		if(savedInstanceState == null) {
			VoteInfoFragment frag = VoteInfoFragment.newInstance(voteId);
			getFragmentManager().beginTransaction().replace(R.id.container, frag, VoteInfoFragment.TAG).commit();
		}
	}
}
