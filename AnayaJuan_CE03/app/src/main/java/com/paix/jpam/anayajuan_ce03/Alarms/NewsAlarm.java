// Juan Pablo Anaya
// MDF3 - 201608
// NewsAlarm

package com.paix.jpam.anayajuan_ce03.Alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce03.Services.NewsFeedIntentService;
import com.paix.jpam.anayajuan_ce03.Utilities.NetworkUtility;

public class NewsAlarm {

    //TAG
    private static final String TAG = "NewsAlarm";

    /*Method to set News Alarm*/
    public void setNewsAlarm(Context context) {

        //Check for Connectivity to start the Service
        NetworkUtility networkUtility = new NetworkUtility();
        if (networkUtility.isConnected(context)) {
            //Set Alarm
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent newsFeedIntentService = new Intent(context, NewsFeedIntentService.class);
            PendingIntent newsFeedPendingIntentService = PendingIntent.getService(context, 1234,
                    newsFeedIntentService, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime()+ 60000, 60000,
                    newsFeedPendingIntentService );
            //Dev
            Log.i(TAG, "setNewsAlarm: " + "ALARM_SET");
        } else {
            //No Network Connection
            Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
        }

    }
}
