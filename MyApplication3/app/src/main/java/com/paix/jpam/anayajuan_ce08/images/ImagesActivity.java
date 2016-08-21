// Juan Pablo Anaya
// MDF3 - 201608
// ImagesActivity

package com.paix.jpam.anayajuan_ce08.images;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.permissions.PermissionsHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class ImagesActivity extends AppCompatActivity implements AsyncListenerInterface {

    //TAG
    private static final String TAG = "ImagesActivity";
    View mLayout; //Snackbar

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
    public void filePathData(ArrayList<String> filePaths) {
        if (!filePaths.isEmpty()) {
            //Dev
            Log.i(TAG, "filePathData: " + filePaths.size());
            for (int i = 0; i < filePaths.size(); i++) {
//                //Dev
//                Log.i(TAG, "filePathData: " + filePaths.get(i));
            }

            //TODO set fragment with data for the adapter
        }
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
