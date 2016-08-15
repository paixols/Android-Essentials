// Juan Pablo Anaya
// MDF3 - 201608
// NotificationReceiver

package com.paix.jpam.anayajuan_ce05.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.paix.jpam.anayajuan_ce05.services.AudioService;

public class NotificationReceiver extends BroadcastReceiver {

    //Tag
    private static final String TAG = "NotificationReceiver";

    //Next & Previous Broadcast
    public static final String CHANGE_NEXT = "com.paix.jpam.anayajuan_ce05.CHANGE_NEXT";
    public static final String CHANGE_PREVIOUS = "com.paix.jpam.anayajuan_ce05.CHANGE_PREVIOUS";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Compare Intent Action
        if (intent.getAction().equals(AudioService.SONG_NEXT)) {
            //Send Broadcast to change to next song on Media Activity
            Intent nextSongBroadcast = new Intent(CHANGE_NEXT);
            LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(nextSongBroadcast);
            //Dev
            Log.i(TAG, "onReceive: " + "NEXT SONG");

        } else if (intent.getAction().equals(AudioService.SONG_PREVIOUS)) {
            //Send Broadcast to change to previous song on Media Activity
            Intent previousSongBroadcast = new Intent(CHANGE_PREVIOUS);
            LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(previousSongBroadcast);
            //Dev
            Log.i(TAG, "onReceive: " + "PREVIOUS SONG");
        }
    }
}
