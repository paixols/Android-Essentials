// Juan Pablo Anaya
// MDF3 - 201608
// PermissionsHelper

package com.paix.jpam.anayajuan_ce08.utilities.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionsHelper {

    //TAG
    //private static final String TAG = "PermissionsHelper";

    /*Request Codes*/
    //MEDIA STORE
    public static final int MY_PERMISSIONS_REQUEST_READ_PHOTOS = 0x0001;

    /*Custom Method to request external storage reading permission*/
    public static Boolean onRequestPermissions(Context context, AppCompatActivity activity) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Permissions Rationale (Background Thread)
                MediaStoreRationale rationale = new MediaStoreRationale(context, activity);
                rationale.execute();
                //Dev
                //Log.i(TAG, "onRequestPermissions: " + "SHOULD SHOW DIALOG RATIONALE");

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_PHOTOS);
                //Dev
                //Log.i(TAG, "onRequestPermissions: " + "PERMISSION BEING REQUESTED");
                return false;
            }
        } else {
            //Permissions Granted
            return true;
        }
        return false;
    }

}
