// Juan Pablo Anaya
// MDF3 - 201608
// MyMapFragment

package com.paix.jpam.anayajuan_ce04.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paix.jpam.anayajuan_ce04.R;

public class MyMapFragment extends MapFragment implements OnMapReadyCallback {

    //TAG
    public static final String TAG = "MyMapFragment";
    /*Properties*/
    private LatLng latLon;
    private GoogleMapOptions mapOptions;
    //Zoom Levels
    int zoomWorld = 1;
    int zoomContinent = 5;
    int zoomCity = 10;
    int zoomStreets = 15;
    int zoomBuildings = 20;
    //Interface
    ToFormAndDetail listener;

    /*Constructor*/
    //Instance Constructor
    public MyMapFragment newInstanceOf(GoogleMapOptions options) {
        //Create Instance
        MyMapFragment myMapFrag = new MyMapFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putParcelable("MapOptions_key", options);
        myMapFrag.setArguments(args);
        //Return Instance
        return myMapFrag;
    }

    /*LifeCycle*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof ToFormAndDetail) {
            listener = (ToFormAndDetail) context;
        } else {
            throw new IllegalArgumentException("Please Add Interface to MyMapFragment");
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //Handle Bundle Arguments
        Bundle args = getArguments();
        if (!handleArguments(args)) {
            //Set Default Location (Full Sail University)
            latLon = new LatLng(0.0, 0.0);
        } else {
            mapOptions = args.getParcelable("MapOptions_key");
        }

        //Initialize Map
        getMapAsync(this);
        //Menu
        setHasOptionsMenu(true);
    }

    /*Map Callback*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: " + "MAP READY");
        Bundle args = getArguments();
        if (!handleArguments(args)) {
            //Handle Default Map
            onMapDefaultConfigure(googleMap);

        } else {

            //Handle Map with custom GoogleMapOptions
            onMapConfigure(googleMap, mapOptions);
        }
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mymap_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.MenuItem_ToForm:
                //MyMapActivity Interface
                listener.toForm();//TODO implement interface correctly
                //DEV
                Log.i(TAG, "onOptionsItemSelected: " + "To Form");
                return true;
        }
        return false;
    }

    /*Fragment Data Handling*/
    private Boolean handleArguments(Bundle args) {
        if (args != null) {
            //If Fragment has Bundle arguments, retrieve the GoogleMapOptions
            mapOptions = args.getParcelable("MapOptions_key");
            return true;
        }
        return false;
    }

    /*Map Configuration*/
    //Default Configuration
    private void onMapDefaultConfigure(GoogleMap googleMap) {
        //Set Map Type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Move Camera to User's Location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLon, zoomStreets));
        Log.i(TAG, "onMapDefaultConfigure: " + "DEFAULT MAP CONFIGURED");
    }

    //Custom Configuration
    private void onMapConfigure(GoogleMap googleMap, GoogleMapOptions mapOptions) {
        googleMap.setMapType(mapOptions.getMapType());
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mapOptions.getCamera()));
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        Log.i(TAG, "onMapConfigure: " + "CUSTOM MAP CONFIGURED");
    }

}
