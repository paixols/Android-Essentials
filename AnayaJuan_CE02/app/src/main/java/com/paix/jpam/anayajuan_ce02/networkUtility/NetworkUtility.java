// Juan Pablo Anaya
// MDF3 - 201608
// NetworkUtility

package com.paix.jpam.anayajuan_ce02.networkUtility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtility {

    //TAG
    private static final String TAG = "NetworkUtility";

    //Check for network connection & Permissions
    public static boolean isConnected(Context context) {

        //Connectivity Manager
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //If we have a manager, get the information
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            //If there is network information available
            if (info != null) {
                //I'm connected !
                if (info.isConnected()) {
                    //Dev
                    Log.i(TAG, "isConnected: " + "Successful Network Connection");
                    //Return true if there is a successful connection !
                    return true;
                }
            }
        }
        return false;
    }

    //Retrieve Image Byte Array
    public static byte[] getNetworkData(String _url) {

        try {
            //Get URL
            URL url = new URL(_url);
            //Make a connection Object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Check for successful response code
            if (connection.getResponseCode() != 200) {
                return new byte[0];
            }
            //If Network Connection is successful
            try {
                //Open the connection
                connection.connect();
                //Get the input stream
                InputStream is = connection.getInputStream();
                //Send input stream to string (Downloaded as Byte Array)
                byte[] data = IOUtils.toByteArray(is);
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

        //If there is no network data return an empty byte array
        return new byte[0];
    }

}
