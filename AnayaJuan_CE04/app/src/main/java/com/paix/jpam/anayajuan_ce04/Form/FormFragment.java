// Juan Pablo Anaya
// MDF3 - 201608
// FormFragment

package com.paix.jpam.anayajuan_ce04.Form;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";

    /*Properties*/
    //Location
    private LatLng latLng;
    //UI
    EditText editTextLat;
    EditText editTextLng;
    ImageView locationImageView;
    //Time Stamp
    Bitmap mBitmap;
    String filePath;

    //Interface
    OnFormMenuSelection listener;

//    /*Constructor*/
//    public FormFragment newInstanceOf(LatLng latLng, Uri uri, String path) {
//        //Set Fragment
//        FormFragment formFrag = new FormFragment();
//        //Set Bundle & Arguments
//        Bundle args = new Bundle();
//        args.putParcelable("LatLng_key", latLng);
//        args.putParcelable("TimeStamp_key", uri);
//        args.putString("FilePath_key",path);
//        formFrag.setArguments(args);
//        //Return Instance
//        return formFrag;
//    }

    /*Constructor*/
    public FormFragment newInstanceOf(LatLng latLng, Bitmap bitmap, String filePath) {
        //Set Fragment
        FormFragment formFrag = new FormFragment();
        //Set Bundle & Arguments
        Bundle args = new Bundle();
        args.putParcelable("LatLng_key", latLng);
        args.putParcelable("Bitmap_key", bitmap);
        args.putString("FilePath_key", filePath);
        formFrag.setArguments(args);
        //Return Instance
        return formFrag;
    }

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnFormMenuSelection) {
            listener = (OnFormMenuSelection) context;
        } else {
            throw new IllegalArgumentException("Please Add OnFormMenuSelection Interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Data if available
        Bundle args = getArguments();
        if (!handleArguments(args)) {
            //Set Default Location (Full Sail University)
            latLng = new LatLng(28.5960, 81.3019);
        }
        //Menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Declare Custom View
        View v = inflater.inflate(R.layout.fragment_form, container, false);
        editTextLat = (EditText) v.findViewById(R.id.EditText_Latitude);
        editTextLng = (EditText) v.findViewById(R.id.EditText_Longitude);
        locationImageView = (ImageView) v.findViewById(R.id.ImageView_Form);
//        locationImageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.earth));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set current Latitude & Longitude
        editTextLat.setText(String.valueOf(latLng.latitude));
        editTextLng.setText(String.valueOf(latLng.longitude));


        File image = null;
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"JAnayaCE04");
        if(path.exists())
        {
            //String[] fileNames = path.list();
            Log.i(TAG, "onViewCreated: " + "Path Exists");
            String [] fileName = path.list();
            Log.i(TAG, "onViewCreated: " + fileName.length);
            String photoName = fileName[fileName.length - 1];
            image = new File(path,photoName);
            locationImageView.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
//            for (int i = 0; i < fileName.length ; i++) {
//                String photoName = fileName[i];
//                Log.i(TAG, "onViewCreated: " + photoName);
//                image = new File(path,photoName);
//                Log.i(TAG, "onViewCreated: " + image.getAbsolutePath());
//                locationImageView.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
//            }
        }

        Bundle args = getArguments();
        if(args.getString("FilePath_key") == null){
            locationImageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.earth));
        }
//        for(int i = 0; i < fileame.length; i++)
//        {
//            Bitmap mBitmap = Bitmap.decodeFile(path.getPath()+"/"+ fileNames[i]);
//            ///Now set this bitmap on imageview
//        }

//        if (mBitmap != null) {
//            locationImageView.setImageBitmap(mBitmap);
//        }
//        //Read Image File
//        if(filePath != null) {
//            File mediaStorageDir;
//            // If the external directory is writable then then return the External pictures directory.
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "JAnayaCE04");
//            } else {
//                mediaStorageDir = Environment.getDownloadCacheDirectory();
//            }
//            File image = null;
//            // Create the storage directory if it does not exist
//            if (mediaStorageDir.exists()) {
//
//                //image = new File(mediaStorageDir,filePath);
//                image = new File(filePath);
//                Log.i(TAG, "onViewCreated: " + "DIRECTORY EXISTS: " + mediaStorageDir.exists());
//                Log.i(TAG, "onViewCreated: " + "IMAGE EXISTS: " + image.exists());
//
//            }
//
//        }

    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_SaveLocation:
                //TODO check for empty EditText Fields
                //TODO send broadcast to save receiver
                //Interface to Form Activity
                listener.saveLocation();
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Save location");
                return true;
            case R.id.MenuItem_StartCamera:
                //TODO start camera
                //TODO after taking picture, display it on the Image View
                //Interface to Start Camera
                listener.openCamera();
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Start Camera");
                return true;
        }
        return false;
    }

    /*Arguments Handling*/
    private Boolean handleArguments(Bundle args) {
        if (args != null) {
            latLng = args.getParcelable("LatLng_key");
            mBitmap = args.getParcelable("Bitmap_key");
            filePath = args.getString("FilePath_key");
            //DEV
            Log.i(TAG, "handleArguments: " + "Arguments are not Null: " + mBitmap);
            if(filePath != null) {
                Log.i(TAG, "handleArguments: " + "File Path: " + filePath);
            }
            return true;
        }
        return false;
    }
}
