// Juan Pablo Anaya
// MDF3 - 201608
// VotingHistoryActivity

package com.fullsail.android.anayajuan_ce10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.android.anayajuan_ce10.fragments.VoteHistoryFragment;
import com.fullsail.android.anayajuan_ce10.storage.Politician;

public class VotingHistoryActivity extends Activity {

    /*Properties*/
    public static final String EXTRA_POLITICIAN = "VotingHistoryActivity.EXTRA_POLITICIAN";
    Politician mPolitician;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Politician
        Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_POLITICIAN)) {
            throw new IllegalArgumentException("You must specify a politician to view the voting history.");
        }

        mPolitician = (Politician) intent.getSerializableExtra(EXTRA_POLITICIAN);

        //Set Politician Name on Action Bar
        if (getActionBar() != null) {
            getActionBar().setTitle(mPolitician.getName());
        }
        //Instance State
        if (savedInstanceState == null) {
            //Set Fragment
            VoteHistoryFragment frag = VoteHistoryFragment.newInstance(mPolitician);
            getFragmentManager().beginTransaction().replace(R.id.container, frag, VoteHistoryFragment.TAG).commit();
        }
    }

    /*Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Save Politician
        if (item.getItemId() == R.id.menu_save) {
            //Start Favorite Politician Saver Service
            Intent intent = new Intent(this, FavoriteSaverService.class);
            intent.putExtra(FavoriteSaverService.EXTRA_POLITICIAN, mPolitician);
            startService(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}