// Juan Pablo Anaya
// MDF3 - 201608
// FormFragment

package com.paix.jpam.anayajuan_ce04.Form;

import android.content.Context;
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
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";

    /*Properties*/
    //Fragment Layout
    private int layoutId;
    //Location
    private LatLng latLng;
    //UI
    EditText editTextLat;
    EditText editTextLng;

    /*Constructor*/
    public static FormFragment newInstanceOf(LatLng latLng) {
        //Set Fragment
        FormFragment formFrag = new FormFragment();
        //Set Bundle & Arguments
        Bundle args = new Bundle();
        args.putParcelable("LatLng_key", latLng);
        formFrag.setArguments(args);
        //Return Instance
        return formFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Data if available
        Bundle args = getArguments();
        if (!handleArguments(args)) {
            latLng = args.getParcelable("LatLng_key");
        } else {
            latLng = new LatLng(0, 0); //Default
        }
        //Menu
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO implement saving interface
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Declare Custom View
        View v = inflater.inflate(R.layout.fragment_form, container, false);
        editTextLat = (EditText) v.findViewById(R.id.EditText_Latitude);
        editTextLng = (EditText) v.findViewById(R.id.EditText_Longitude);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set current Latitude & Longitude
        editTextLat.setText(String.valueOf(latLng.latitude));
        editTextLng.setText(String.valueOf(latLng.longitude));
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MenuItem_SaveLocation:
                //TODO check for empty EditText Fields
                //TODO send broadcast to save receiver
                //Dev
                Log.i(TAG, "onOptionsItemSelected: " + "Save location");
                return true;
            case R.id.MenuItem_StartCamera:
                //TODO start camera
                //TODO after taking picture, display it on the Image View
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
            return true;
        }
        return false;
    }
}
