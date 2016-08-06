// Juan Pablo Anaya
// MDF3 - 201608
// NewsFeedHelper

package com.paix.jpam.anayajuan_ce03.Services;

import android.content.Context;
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

 class NewsFeedHelper {
    
    //TAG
    private static final String TAG = "NewsFeedHelper";

    /*Method for Parsing News Feed Articles*/
    public void parseNewsFeed(Context context){

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
            Toast.makeText(context, "Check Network Connection", Toast.LENGTH_SHORT).show();
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
                    //Get Thumbnail URL (Small Image)
                    thumbnailUrl = thumbnail.getJSONObject(0).getString("url");
                    for (int j = 0; j < thumbnail.length(); j++) {
                        String format = thumbnail.getJSONObject(j).getString("format");
                        if (format.equals("mediumThreeByTwo210")) {
                            //Get Image URL (Medium Image)
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
        storageUtility.writeInternalStorage(newsArticles, context, context.getString(R.string.newsFeedFile));
    }
}
