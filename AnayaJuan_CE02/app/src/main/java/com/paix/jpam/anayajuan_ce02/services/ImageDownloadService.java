// Juan Pablo Anaya
// MDF3 - 201608
// ImageDownloadService

package com.paix.jpam.anayajuan_ce02.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.paix.jpam.anayajuan_ce02.networkUtility.NetworkUtility;
import com.paix.jpam.anayajuan_ce02.storage.ExternalStorage;

public class ImageDownloadService extends IntentService {

    //TAG
    private static final String TAG = "ImageDownloadService";
    //Broadcast Action
    public static final String ACTION_GRIDVIEW_UPDATE =
            "com.paix.jpam.j_anaya_ce02.android.ACTION_GRIDVIEW_UPDATE";
    //Images suffix array
    private final String[] IMAGES = {
            "WJB086a.jpg", "sWydfiZ.jpg", "g4x7Ate.jpg",
            "KKAt6uj.jpg", "JYgyi5J.jpg", "GXKBEwM.jpg",
            "qvUqb39.jpg", "KKL0zCt.jpg", "iHpQIVr.jpg",
            "iHpQIVr.jpg", "skssLZk.jpg", "MhH358I.jpg",
            "WV17m5P.jpg", "xIeXpgk.jpg", "xIeXpgk.jpg",
            "opI9jXc.jpg", "WxvCY5C.jpg", "PS3auwq.jpg",
            "DuAaARL.jpg", "Tm1rWRq.jpg", "Br8O8jt.jpg",
            "HWw8PTE.jpg", "cuWL0fU.jpg", "dLJAONk.jpg",
            "CWJZqTP.jpg", "Rixk08S.jpg", "7m2DpDY.jpg",
            "fUvQR7d.jpg", "ZQAlNxD.jpg", "AXuP8za.jpg"
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
                String URL_BASE = "http://imgur.com/";
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
