// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedFavoriteReceiver

package com.paix.jpam.anayajuan_ce03.Receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.paix.jpam.anayajuan_ce03.NewsFeed.News;
import com.paix.jpam.anayajuan_ce03.R;
import com.paix.jpam.anayajuan_ce03.Services.NewsFeedIntentService;
import com.paix.jpam.anayajuan_ce03.Utilities.StorageUtility;

import java.util.ArrayList;

public class NewsFeedFavoriteReceiver extends BroadcastReceiver {

    //TAG
    private static final String TAG = "NewsFeedFavoriteReceiver";
    //Broadcast Actions
    public static final String UPDATE_BROADCAST = "com.paix.jpam.anayajuan_ce03.UPDATE_BROADCAST";


    @Override
    public void onReceive(Context context, Intent intent) {
        /*Storage Utility*/
        StorageUtility storageUtility = new StorageUtility();
        //Array for Favorite News Feed
        ArrayList<News> favoriteNewsFeed;

        //Save Article to Favorite News Feed
        if (intent.getAction().equals(NewsFeedIntentService.NOTIFICATION_SAVE_BROADCAST)) {
            //Collapse Notification Drawer (with Notification ID)
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent.getIntExtra(context.getString(R.string.cancelNotification),0));
            //Save to Favorite News Articles
            News savedNewsArticle = (News) intent.getSerializableExtra
                    (context.getString(R.string.favorite_news_key));
            //Read Favorites News Feed from Internal Storage
            favoriteNewsFeed = storageUtility.readInternalStorage(context,
                    context.getString(R.string.newsFeedFavoriteFile));
            if (favoriteNewsFeed.isEmpty()) {
                favoriteNewsFeed = new ArrayList<>();
                favoriteNewsFeed.clear();
            }
            favoriteNewsFeed.add(savedNewsArticle);
            storageUtility.writeInternalStorage(favoriteNewsFeed, context,
                    context.getString(R.string.newsFeedFavoriteFile));

            //Send Update Broadcast for User Interface (NewsFeedActivity)
            updateBroadcast(context);
        }
    }

    /*Broadcasts*/
    //Update Broadcast
    private void updateBroadcast(Context context) {
        Intent updateBroadcastIntent = new Intent(UPDATE_BROADCAST);
        context.sendBroadcast(updateBroadcastIntent);
    }
}
