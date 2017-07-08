// Juan Pablo Anaya
// MDF3 - CE02
// Main Activity

package com.paix.jpam.anayajuan_ce02;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce02.services.ImageDownloadService;
import com.paix.jpam.anayajuan_ce02.gridView.GridViewFragment;
import com.paix.jpam.anayajuan_ce02.networkUtility.NetworkUtility;


public class MainActivity extends AppCompatActivity {


    //TAG
    private static final String TAG = "MainActivity";
    //Broadcast Receiver
    private UpdateReceiver mReceiver;
    //External Storage Permission
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x0001;
    //Layout for Snack_bar reference
    private View mLayout;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.linearLayout_activity_main);
        //Set GridViewFragment
        GridViewFragment gridViewFrag = new GridViewFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_GridView_FragHolder
                , gridViewFrag, GridViewFragment.TAG).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register Broadcast Receiver
        mReceiver = new UpdateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ImageDownloadService.ACTION_GRIDVIEW_UPDATE);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Un-register Broadcast Receiver
        unregisterReceiver(mReceiver);
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
                        //Check For Connectivity
                        if (NetworkUtility.isConnected(this)) {
                            //Start ImageDownloadService
                            Intent imageDownloadServiceIntent = new Intent(this, ImageDownloadService.class);
                            startService(imageDownloadServiceIntent);
                            //Dev
                            Log.i(TAG, "onOptionsItemSelected: " + "Start Intent Service for Image Download");
                            //Service started successfully
                            return true;
                        } else {
                            //Let the user know there is no Network Connection
                            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
        return false;
    }

    /*Permissions Requests*/
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
            //Permissions for Writing to External Storage have not been granted, request directly
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    /*Permissions Request Results*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            //Received permission result for External Storage
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Let the user know the permissions have been granted
                Snackbar.make(mLayout, "External Storage permissions granted !", Snackbar.LENGTH_SHORT).show();
                //Start the first download service
                Intent imageDownloadServiceIntent = new Intent(this, ImageDownloadService.class);
                startService(imageDownloadServiceIntent);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /*Broadcast Receiver*/
    private class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Set new GridView Fragment
            GridViewFragment gridViewFrag = (GridViewFragment) getSupportFragmentManager().findFragmentByTag(GridViewFragment.TAG);
            gridViewFrag.refreshAdapter();
        }
    }
}
