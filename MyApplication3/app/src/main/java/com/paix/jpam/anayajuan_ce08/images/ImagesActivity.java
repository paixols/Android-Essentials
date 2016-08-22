// Juan Pablo Anaya
// MDF3 - 201608
// ImagesActivity

package com.paix.jpam.anayajuan_ce08.images;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.utilities.permissions.PermissionsHelper;


public class ImagesActivity extends AppCompatActivity implements AsyncListenerInterface {

    //TAG
    private static final String TAG = "ImagesActivity";
    private View mLayout; //Snackbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        mLayout = findViewById(R.id.RelativeLayout_Images_Activity);

        if (PermissionsHelper.onRequestPermissions(this, this)) {
            //Dev
            Log.i(TAG, "onCreate: " + "Permissions Granted");
            //Query Device Images
            ThumbnailTask task = new ThumbnailTask(this);
            task.execute();
        }
    }

    @Override
    public void filePathData(Cursor cursor) {
        //Data checkpoint
        if (cursor == null) {
            Snackbar.make(mLayout, "No Images on External Storage !", Snackbar.LENGTH_SHORT).show();
            return;
        }
        //Set Fragment with Cursor Info
        ImagesFragment imagesFragment = new ImagesFragment().newInstanceOf(cursor);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_GridViewHolder, imagesFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsHelper.MY_PERMISSIONS_REQUEST_READ_PHOTOS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    Log.i(TAG, "onRequestPermissionsResult: " + "PERMISSION GRANTED" +
//                            Arrays.toString(permissions));

                    //Execute Task to get All the Images From Public External Storage
                    ThumbnailTask thumbnailTask = new ThumbnailTask(this);
                    thumbnailTask.execute();
                } else {
//                    //Dev
//                    Log.i(TAG, "onRequestPermissionsResult: " + "PERMISSION DENIED");
                    Snackbar.make(mLayout, "Can't access photos without permission!",
                            Snackbar.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
