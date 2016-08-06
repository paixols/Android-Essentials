// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedBootReceiver

package com.paix.jpam.anayajuan_ce03.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.paix.jpam.anayajuan_ce03.Alarms.NewsAlarm;

public class NewsFeedBootReceiver extends BroadcastReceiver {

    /*LifeCycle*/
    @Override
    public void onReceive(Context context, Intent intent) {

        //Check for the BOOT_COMPLETED intent
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //Set Alarm (NewsAlarm)
            NewsAlarm newsAlarm = new NewsAlarm();
            newsAlarm.setNewsAlarm(context);
        }

    }
}
