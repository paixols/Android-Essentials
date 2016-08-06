// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedActivity

package com.paix.jpam.anayajuan_ce03.NewsFeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce03.Alarms.NewsAlarm;
import com.paix.jpam.anayajuan_ce03.R;
import com.paix.jpam.anayajuan_ce03.Receivers.NewsFeedFavoriteReceiver;

public class NewsFeedActivity extends AppCompatActivity implements NewsFeedListener {

    /*Properties*/
    //TAG
    private static final String TAG = "NewsFeedActivity";
    //Broadcast Receiver (Update UI)
    private UpdateReceiver mReceiver;

    /*Life Cycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set News List Fragment
        setNewsListFragment();
        //Set Alarm
        NewsAlarm newsAlarm = new NewsAlarm();
        newsAlarm.setNewsAlarm(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register Broadcast Receivers
        mReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NewsFeedFavoriteReceiver.UPDATE_BROADCAST);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Un-register Broadcast Receivers
        unregisterReceiver(mReceiver);
    }

    /*Public Interface for Favorite News Article Selected on News List Fragment*/
    @Override
    public void itemSelected(News newsArticle) {
        //Set Explicit Intent for Web View
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        if (!newsArticle.getNewsUrl().equals("")) {
            webIntent.setData(Uri.parse(newsArticle.getNewsUrl()));
        } else {
            Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
        }
        startActivity(webIntent);
    }

    /*Broadcast Receiver for UI - Updates*/
    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NewsFeedFavoriteReceiver.UPDATE_BROADCAST)) {
                //Show new Notification
                setNewsListFragment();
                //Dev
                Log.i(TAG, "onReceive: " + "UI-UPDATED");
            }
        }
    }

    /*Method for Setting New NewsListFragment*/
    private void setNewsListFragment() {
        NewsListFragment newsListFragment = new NewsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_FragmentList,
                newsListFragment).commit();
    }
}
