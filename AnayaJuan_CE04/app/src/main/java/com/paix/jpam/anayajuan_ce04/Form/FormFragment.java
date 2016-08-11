// Juan Pablo Anaya
// MDF3 - 201608
// FormFragment

package com.paix.jpam.anayajuan_ce04.form;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.paix.jpam.anayajuan_ce04.utilities.ImageLocation;
import com.paix.jpam.anayajuan_ce04.utilities.StorageHelper;


public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";

    /*Properties*/
    //Location
    private LatLng latLng;
    //UI
    private EditText editTextLat;
    private EditText editTextLng;
    private ImageView locationImageView;
    //File Path
    private String filePath;
    //Interface
    private OnFormMenuSelection listener;


    /*Constructor*/
    public FormFragment newInstanceOf(LatLng latLng, String filePath) {
        //Set Fragment
        FormFragment formFrag = new FormFragment();
        //Set Bundle & Arguments
        Bundle args = new Bundle();
        args.putParcelable("LatLng_key", latLng);
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
        //Declare Custom View & UI
        View v = inflater.inflate(R.layout.fragment_form, container, false);
        editTextLat = (EditText) v.findViewById(R.id.EditText_Latitude);
        editTextLng = (EditText) v.findViewById(R.id.EditText_Longitude);
        locationImageView = (ImageView) v.findViewById(R.id.ImageView_Form);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set current Latitude & Longitude
        editTextLat.setText(String.valueOf(latLng.latitude));
        editTextLng.setText(String.valueOf(latLng.longitude));

        StorageHelper storageHelper = new StorageHelper();
        Bitmap bm = null;
        if (storageHelper.getLastBitmapFromFileFolder() != null) {
            bm = storageHelper.getLastBitmapFromFileFolder();
        }
        if (bm != null) {
            locationImageView.setImageBitmap(bm);
        }
        Bundle args = getArguments();
        if (args.getString("FilePath_key") == null) {
            locationImageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.earth));
        }
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
                ImageLocation imageLocation = new ImageLocation(latLng.latitude, latLng.longitude, filePath);
                //Interface to Form Activity
                listener.saveLocation(imageLocation);
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Save location");
                return true;
            case R.id.MenuItem_StartCamera:
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
            //mBitmap = args.getParcelable("Bitmap_key");
            filePath = args.getString("FilePath_key");
            //DEV
            //Log.i(TAG, "handleArguments: " + "Arguments are not Null: " + mBitmap);
            if (filePath != null) {
                Log.i(TAG, "handleArguments: " + "File Path: " + filePath);
            }
            return true;
        }
        return false;
    }
}
