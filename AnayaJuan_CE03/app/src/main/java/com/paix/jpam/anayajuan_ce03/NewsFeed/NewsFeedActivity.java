// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedActivity

package com.paix.jpam.anayajuan_ce03.NewsFeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce03.Alarms.NewsAlarm;
import com.paix.jpam.anayajuan_ce03.R;
import com.paix.jpam.anayajuan_ce03.Services.NewsFeedIntentService;

public class NewsFeedActivity extends AppCompatActivity implements NewsFeedListener {

    /*Properties*/
    //TAG
    private static final String TAG = "NewsFeedActivity";
    //Broadcast Receiver
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
//        //Register Broadcast Receivers
//        mReceiver = new UpdateReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(NewsFeedIntentService.NOTIFICATION_SHOW);
//        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        //Un-register Broadcast Receivers
//        unregisterReceiver(mReceiver);
    }

    /*Public Interface for Favorite News Article Selected on News List Fragment*/
    @Override
    public void itemSelected(News newsArticle) {

    }

    /*Broadcast Receiver for UI - Updates*/
    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(NewsFeedIntentService.NOTIFICATION_SHOW)) {
//                //Show new Notification
//                setNewsListFragment();
//                //Dev
//                Log.i(TAG, "onReceive: " + "UI-UPDATED");
//            }
        }
    }

    /*Method for Setting New NewsListFragment*/
    private void setNewsListFragment() {
        NewsListFragment newsListFragment = new NewsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_FragmentList,
                newsListFragment).commit();
    }
}
