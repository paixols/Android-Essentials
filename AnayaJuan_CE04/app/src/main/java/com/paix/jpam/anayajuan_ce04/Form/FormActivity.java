// Juan Pablo Anaya
// MDF3 - 201608
// FormActivity

package com.paix.jpam.anayajuan_ce04.form;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.map.MyMapActivity;
import com.paix.jpam.anayajuan_ce04.R;
import com.paix.jpam.anayajuan_ce04.utilities.ImageLocation;
import com.paix.jpam.anayajuan_ce04.utilities.StorageHelper;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity implements OnFormMenuSelection {

    //TAG
    private static final String TAG = "FormActivity";

    /*Properties*/
    private View mLayout;
    //External Storage Permission
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x2000;
    //Camera Storage
    private static final int CAPTURE_FORM_ACTIVITY_REQUEST_CODE = 0x3000;
    /*Camera*/
    private String mCurrentPhotoPath; //Used for Fragment
    /*Location*/
    private LatLng latLng;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Snackbar Layout
        mLayout = findViewById(R.id.LinearLayout_FormActivity);
        //Get LatLng Info (From MyMapActivity)
        Intent intent = getIntent();
        latLng = intent.getParcelableExtra("LatLng_value");
        Log.i(TAG, "onCreate: " + latLng.latitude + "/" + latLng.longitude);

        //Set Form Fragment (Without Thumbnail)
        FormFragment formFragment = new FormFragment().newInstanceOf(latLng, null);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_FormHolder, formFragment).commit();
        //Create Image Folder for the First Time
        StorageHelper storageHelper = new StorageHelper();
        storageHelper.getOutputMediaFile();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Set New Fragment (With Thumbnail)
        FormFragment formFrag = new FormFragment().newInstanceOf(latLng, mCurrentPhotoPath);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_FormHolder,
                formFrag).commit();
    }

    /*Save Location*/
    @Override
    public void saveLocation(ImageLocation imageLocation) {
        //Check SDK version (For permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If Build Version >= API 23, request permissions at runtime
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //Request Permissions
                requestExternalStoragePermissions();
            } else {
                //Dev
                Log.i(TAG, "saveLocation: " + imageLocation.getLat() + "/" + imageLocation.getLng() + "/" +
                        imageLocation.getFilePath());
                //Save Image with Location Data
                StorageHelper storageHelper = new StorageHelper();
                ArrayList imageLocations;
                imageLocations = storageHelper.readInternalStorage(this);
                if (imageLocations == null) {
                    imageLocations = new ArrayList<>();
                    imageLocations.clear();
                }
                imageLocations.add(imageLocation);
                storageHelper.writeInternalStorage(imageLocations, this);
                //Navigate Back to Map
                Intent updateMapIntent = new Intent(MyMapActivity.UPDATE_MAP);
                this.sendBroadcast(updateMapIntent);
                this.finish();
            }
        }
        //Dev
        Log.i(TAG, "saveLocation: " + "Save Location Interface");
    }

    /*Camera*/
    //On Open Camera
    @Override
    public void openCamera() {

        //Check SDK version (For permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If Build Version >= API 23, request permissions at runtime
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //Request Permissions
                requestExternalStoragePermissions();
            } else {

                createCameraIntent();

            }
        }

    }

    private void createCameraIntent() {
        //Create Photo File & Path
        StorageHelper storageHelper = new StorageHelper();
        mCurrentPhotoPath = storageHelper.getCurrentFilePath(storageHelper.getOutputMediaFile());
        Uri photoUri = Uri.parse(mCurrentPhotoPath);


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //Comment to get Thumbnail
            Log.i(TAG, "openCamera: " + "URI: " + photoUri.toString());
            startActivityForResult(takePictureIntent, CAPTURE_FORM_ACTIVITY_REQUEST_CODE);
        }
        //Dev
        Log.i(TAG, "openCamera: " + "Open Camera Interface");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_FORM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //Dev
                Log.i(TAG, "onActivityResult: " + "RESULT_OK");
            }

        } else if (resultCode == RESULT_CANCELED) {

            Snackbar.make(mLayout, "Didn't like the photo?", Snackbar.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);

        }
    }


    /*Permissions Requests*/
    //Internal Storage Permission Request (Runtime)
    private void requestExternalStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            /*Provide permissions rationale if the permission was not granted previously*/
            Snackbar.make(mLayout, "Storage permission needed to save location", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(FormActivity.this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();
        } else {
            //Permissions for Writing to External Storage have not been granted, request directly
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    //On Request Permissions Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //External Storage
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            //Received permission result for External Storage
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Let the user know the permissions have been granted
                Snackbar.make(mLayout, "External Storage permissions granted !", Snackbar.LENGTH_SHORT).show();

                createCameraIntent();

            } else {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }
}