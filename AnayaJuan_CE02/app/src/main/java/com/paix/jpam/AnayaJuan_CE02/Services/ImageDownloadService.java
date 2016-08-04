// Juan Pablo Anaya
// MDF3 - 201608
// ImageDownloadService

package com.paix.jpam.anayajuan_ce02.Services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.paix.jpam.anayajuan_ce02.NetworkUtility.NetworkUtility;
import com.paix.jpam.anayajuan_ce02.Storage.ExternalStorage;

public class ImageDownloadService extends IntentService {

    //TAG
    private static final String TAG = "ImageDownloadService";
    //Broadcast Action
    public static final String ACTION_GRIDVIEW_UPDATE =
            "com.paix.jpam.j_anaya_ce02.android.ACTION_GRIDVIEW_UPDATE";
    //Images suffix array
    private final String[] IMAGES = {
            "MgmzpOJ.jpg", "VZmFngH.jpg", "ptE5z9u.jpg",
            "4QKO8Up.jpg", "Vm2UdDH.jpg", "C040ctB.jpg",
            "MScR8za.jpg", "tM1bsAH.jpg", "fS1lKZx.jpg",
            "h8e5rBX.jpg", "KBtUxzq.jpg", "wYXWJZz.jpg",
            "LOUwRC4.jpg", "7ZSQfIu.jpg", "XLJiKqp.jpg",
            "nXVLE9W.jpg", "HYQuj4b.jpg", "R8YIb8d.jpg",
            "cLv3TVc.jpg", "f7pMMdA.jpg", "Dl1aIHV.jpg",
            "UE3ng26.jpg", "1oyYfr0.jpg", "YSJ28fr.jpg",
            "Ey39hl5.jpg", "HAnhjCI.jpg", "En3J4ZF.jpg",
            "wr65Geg.jpg", "7D35kbV.jpg", "Z2WQBPI.jpg"
    };

    //Required Constructor (Thread name)
    public ImageDownloadService() {
        super("Image Download Service");
    }

    /*LifeCycle*/
    @Override
    protected void onHandleIntent(Intent intent) {

        /*Long Running Operation*/
        for (String IMAGE : IMAGES) {
            //Check for image duplicates
            if (!ExternalStorage.imageExists(this, IMAGE)) {
                //If there's no duplicate
                //Build URL
                String URL_BASE = "http://i.imgur.com/";
                String uri = URL_BASE + IMAGE;

                //Get Image as Byte Array
                byte[] image = NetworkUtility.getNetworkData(uri);

                //Null check
                if (image != null) {
                    //Write to external Storage
                    ExternalStorage.writeExternal(getApplicationContext(), IMAGE, image);
                }
            }

            //Call Update Broadcast method
            updateBroadcast();
        }
    }

    /*Broadcast for GridView Update*/
    private void updateBroadcast() {
        Intent updateBroadcastIntent = new Intent(ACTION_GRIDVIEW_UPDATE);
        getApplicationContext().sendBroadcast(updateBroadcastIntent);
        //Dev
        Log.i(TAG, "updateBroadcast: " + "Broadcast Sent");
    }
}
