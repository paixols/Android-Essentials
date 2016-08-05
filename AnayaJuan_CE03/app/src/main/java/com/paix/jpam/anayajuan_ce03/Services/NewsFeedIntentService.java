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
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce03.NewsFeed.News;
import com.paix.jpam.anayajuan_ce03.R;
import com.paix.jpam.anayajuan_ce03.Utilities.NetworkUtility;
import com.paix.jpam.anayajuan_ce03.Utilities.StorageUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        //News Articles Holder
        ArrayList<News> newsArticles = new ArrayList<>();
        //Network Utility Instance
        NetworkUtility networkUtility = new NetworkUtility();

        //Technology News from New York Times Top Stories API
        String url = "https://api.nytimes.com/svc/topstories/v2/technology.json" +
                "?api-key=433baca6b8854062bbcb88deb10ca34e";
        String data = networkUtility.getNetworkData(url);

        //Check for Network Error
        if (data.equals("Error")) {
            Log.i(TAG, "doInBackground: " + "NO DATA AVAILABLE");
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }

        //Parse JSON DATA
        try {
            //Outer Object
            JSONObject response = new JSONObject(data);

            //News
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                //Get News Info
                JSONObject newsObject = results.getJSONObject(i);
                String section = newsObject.getString("section");
                String subsection = newsObject.getString("subsection");
                String title = newsObject.getString("title");
                String newsUrl = newsObject.getString("url");
                String description = newsObject.getString("abstract");
                //Thumbnail & Detail Image
                JSONArray thumbnail = newsObject.getJSONArray("multimedia");
                String thumbnailUrl = "";
                String imageUrl = "";

                //If Thumbnail is available, retrieve the correct sized one
                if (thumbnail.length() > 0) {
                    thumbnailUrl = thumbnail.getJSONObject(0).getString("url");
                    for (int j = 0; j < thumbnail.length(); j++) {
                        String format = thumbnail.getJSONObject(j).getString("format");
                        if (format.equals("mediumThreeByTwo210")) {
                            imageUrl = thumbnail.getJSONObject(j).getString("url");
                        }
                    }
                }

                //Dev (News Download Title)
                Log.i(TAG, "doInBackground: TITLE: " + title);

                //Build News Article Object
                News news = new News(section, subsection, title, newsUrl, thumbnailUrl,
                        description, imageUrl);
                //Add News Article to News Articles ArrayList<>
                newsArticles.add(news);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Dev
        Log.i(TAG, "onHandleIntent: " + newsArticles.size());

        //Save to Internal Storage
        StorageUtility storageUtility = new StorageUtility();
        storageUtility.writeInternalStorage(newsArticles, this, this.getString(R.string.newsFeedFile));

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
        int randomMaxValue = 1;
        if(newsList.size() != 0) {
            randomMaxValue = randomNewsArticleIndex.nextInt(newsList.size());
        }
        News randomNewsArticle = (News) newsList.get(randomMaxValue);
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
