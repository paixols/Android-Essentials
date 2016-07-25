// Juan Pablo Anaya
// MDF3 - CE02
// Main Activity
package com.paix.jpam.j_anaya_ce02;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "MainActivity";
    //External Storage Permission
    private static String[] PERMISSION_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x0001;
    //Layout for Snackbar reference
    private View mLayout;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.linearLayout_activity_main);

    }

    /*Menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_download:
                //Check SDK version (For permissions)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //If Build Version >= API 23, request permissions at runtime
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //If permissions haven't been granted, request them
                        requestExternalStoragePermissions();
                    } else {
                        //TODO start intent service for image download
                        //Dev
                        Log.i(TAG, "onOptionsItemSelected: " + "Start Intent Service for Image Download");
                        return true;
                    }
                }
        }
        return false;
    }

    //Internal Storage Permission Request (Runtime)
    private void requestExternalStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            /*Provide permissions rationale if the permission was not granted previously*/
            Snackbar.make(mLayout, "Storage permission needed to download images", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();
        } else {
            //Permissions for Writting to External Storage have not been granted, request directly
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }
}
