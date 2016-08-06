// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedIntentService

package com.paix.jpam.anayajuan_ce03.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.paix.jpam.anayajuan_ce03.NewsFeed.News;
import com.paix.jpam.anayajuan_ce03.R;
import com.paix.jpam.anayajuan_ce03.Utilities.StorageUtility;

import java.util.ArrayList;
import java.util.Random;

public class NewsFeedIntentService extends IntentService {

    /*Properties*/
    private static final String TAG = "NewsFeedIntentService";
    //Broadcast Actions
    public static final String NOTIFICATION_SAVE_BROADCAST =
            "com.paix.jpam.anayajuan_ce03.NOTIFICATION_SAVE_BROADCAST";
    //Notification
    private static final int EXPANDED_NOTIFICATION = 0xDEADBEEA;


    /*Default Constructor*/
    public NewsFeedIntentService() {
        super("NewsFeed Intent Service");
    }

    /*LifeCycle*/
    @Override
    protected void onHandleIntent(Intent intent) {

        //Parse News Feed
        NewsFeedHelper newsFeedHelper = new NewsFeedHelper();
        newsFeedHelper.parseNewsFeed(this);

        //Build Notification
        buildNotification();
    }

    /*Notification Builder*/
    //Build notification with random news article
    private void buildNotification() {

        //Get Random News Article
        StorageUtility storageUtility = new StorageUtility();
        ArrayList<News> newsList = storageUtility.readInternalStorage(this,
                this.getString(R.string.newsFeedFile));
        Random randomNewsArticleIndex = new Random();
        int randomMaxValue = randomNewsArticleIndex.nextInt(newsList.size());
        News randomNewsArticle = newsList.get(randomMaxValue);
        //Dev
        Log.i(TAG, "buildNotification: Random News Article Name: " + randomNewsArticle.getTitle());

        //Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Standard Notification for earlier Android Versions
        builder.setSmallIcon(R.drawable.newspin);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.newsicon));
        builder.setContentTitle(randomNewsArticle.getTitle());
        builder.setContentText(randomNewsArticle.getSection());
        //Expanded Notification for new Android Versions
        NotificationCompat.BigTextStyle bigBuilder = new NotificationCompat.BigTextStyle();
        bigBuilder.setBigContentTitle(randomNewsArticle.getTitle());
        bigBuilder.setSummaryText(randomNewsArticle.getSection());
        bigBuilder.bigText(randomNewsArticle.getDescription());
        builder.setStyle(bigBuilder);

        //Notification Save Action Button (Save News Article to Favorites)
        Intent saveNewsArticleIntent = new Intent(NOTIFICATION_SAVE_BROADCAST);
        saveNewsArticleIntent.putExtra(this.getString(R.string.favorite_news_key),
                randomNewsArticle);
        saveNewsArticleIntent.putExtra(getApplicationContext().getString(R.string.cancelNotification),
                EXPANDED_NOTIFICATION); //Used to collapse Notification Drawer when the Save button is pressed
        PendingIntent saveNewsArticlePendingIntent = PendingIntent.getBroadcast
                (this, 0, saveNewsArticleIntent, PendingIntent.FLAG_UPDATE_CURRENT); // Pending Intent 0
        builder.addAction(android.R.drawable.ic_menu_add, "Save Article", saveNewsArticlePendingIntent);


        //Intent to open News in default web browser
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(randomNewsArticle.getNewsUrl()));
        //Box in pending intent & set it to the Notification Body
        PendingIntent webPendingIntent = PendingIntent.getActivity(this, 1,
                webIntent, PendingIntent.FLAG_UPDATE_CURRENT);//Pending Intent 1
        builder.setContentIntent(webPendingIntent);
        builder.setAutoCancel(true);

        //Notification Manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(EXPANDED_NOTIFICATION, builder.build());
    }

}//NewsFeedIntentService END
