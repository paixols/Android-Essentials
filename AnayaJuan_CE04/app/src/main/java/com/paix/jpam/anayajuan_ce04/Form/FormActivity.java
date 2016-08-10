// Juan Pablo Anaya
// MDF3 - 201608
// FormActivity

package com.paix.jpam.anayajuan_ce04.Form;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity implements OnFormMenuSelection {

    //TAG
    private static final String TAG = "FormActivity";

    /*Properties*/
    View mLayout;
    //External Storage Permission
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 0x2000;
    //Camera Storage
    private static final int CAPTURE_FORM_ACTIVITY_REQUEST_CODE = 0x3000;
    /*Camera*/
    Bitmap bitmap;
    String mCurrentPhotoPath;
    Uri photoUri;
    File photoFile;
    /*Location*/
    LatLng latLng;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if(savedInstanceState != null){
            mCurrentPhotoPath = savedInstanceState.getString("filepath_key");
        }

        //Snackbar Layout
        mLayout = findViewById(R.id.LinearLayout_FormActivity);
        //Get LatLng Info (From MyMapActivity)
        Intent intent = getIntent();
        latLng = intent.getParcelableExtra("LatLng_value");
        Log.i(TAG, "onCreate: " + latLng.latitude + "/" + latLng.longitude);

        //Set Form Fragment (Without Thumbnail)
        FormFragment formFragment = new FormFragment().newInstanceOf(latLng, null, null);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_FormHolder, formFragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("filepath_key", mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Set New Fragment (With Thumbnail)
        FormFragment formFrag = new FormFragment().newInstanceOf(latLng, bitmap, mCurrentPhotoPath);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_FormHolder,
                formFrag).commit();
    }

    /*Save Location*/
    @Override
    public void saveLocation() {
        //Check SDK version (For permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If Build Version >= API 23, request permissions at runtime
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //Request Permissions
                requestExternalStoragePermissions();
            } else {
                //TODO Save IMAGE with LocationTag to Private External Storage

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
            }
        }
        //Create Photo File & Path
        photoFile = null;
       // try {
            //photoFile = createImageFile();
            photoFile = getOutputMediaFile();
            photoUri = Uri.parse(mCurrentPhotoPath);
       // }
//        catch (IOException e) {
//            e.printStackTrace();
//        }


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //Comment to get Thumbnail
            Log.i(TAG, "openCamera: " + "URI: " + photoUri.toString());
            startActivityForResult(takePictureIntent, CAPTURE_FORM_ACTIVITY_REQUEST_CODE);
        }


        //TODO Put picture on the Image View of the Form Fragment ImageView (REFRESH IMAGE VIEW)
        //Dev
        Log.i(TAG, "openCamera: " + "Open Camera Interface");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_FORM_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = (Bitmap) extras.get("data");
                    Log.i(TAG, "onActivityResult: " + bitmap);
                }

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

                //TODO Save current location

            } else {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }

    /*Create Image File - Android Developer Portal Code Snippet*/
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_" + ".jpg";
//        String folderName = "JAnayaCE04";
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + folderName);
//
//        if(!storageDir.mkdir()){
//            Log.i(TAG, "createImageFile: " + "STORAGE NOT CREATED");
//        }
//
//        //File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////        File image = File.createTempFile(
////                imageFileName,  /* prefix */
////                ".jpg",         /* suffix */
////                storageDir      /* directory */
////        );
//        File image = new File(storageDir, imageFileName);
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//        return image;
//    }


    public File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir;
        // If the external directory is writable then then return the External pictures directory.
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "JAnayaCE04");
        } else {
            mediaStorageDir = Environment.getDownloadCacheDirectory();
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        mCurrentPhotoPath = "file:" + mediaFile.getAbsolutePath();
        //mCurrentPhotoPath = "IMG_" + timeStamp + ".jpg";
        return mediaFile;
    }
}