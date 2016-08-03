// Juan Pablo Anaya
// MDF3 - 201608
// NetworkUtility
package com.paix.jpam.anayajuan_ce03.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtility {

    //TAG
    private static final String TAG = "NetworkUtility";

    //Check for Network Connection & Permissions
    public boolean isConnected(Context context) {
        //Connectivity Manager
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //If we have a manager, get the information
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            //If there is network information available
            if (info != null) {
                //I'm connected !
                if (info.isConnected()) {
                    //Return true if there is a successful connection !
                    return true;
                }
            }
        }

        return false;
    }

    //Get Network Utility
    public String getNetworkData(String _url) {

        try {
            //Get URL
            URL url = new URL(_url);
            //Make a connection Object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Check for successful response code
            if (connection.getResponseCode() != 200) {
                return "Error";
            }

            try {
                //Open the connection
                connection.connect();
                //Get the input stream
                InputStream is = connection.getInputStream();
                //Send input stream to string
                String data = IOUtils.toString(is);
                //When it's done, close the connection (Required)
                is.close();
                //Return Stringified Data
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //If there is no network data return "Error"
        return "Error";
    }

    //Get News Image and decode to Bitmap for Notification Icon
    public Bitmap gitBitmapFromUrl(String _thumbnailUrl) {
        //If there is a thumbnail Url Available
        if (!_thumbnailUrl.equals("")) {

            try {
                URL url = new URL(_thumbnailUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
