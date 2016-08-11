// Juan Pablo Anaya
// MDF3 - 201608
// MyMapFragment

package com.paix.jpam.anayajuan_ce04.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import com.paix.jpam.anayajuan_ce04.utilities.ImageLocation;
import com.paix.jpam.anayajuan_ce04.utilities.StorageHelper;

import java.util.ArrayList;

public class MyMapFragment extends MapFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    //TAG
    public static final String TAG = "MyMapFragment";
    /*Properties*/
    private LatLng latLon;
    private GoogleMapOptions mapOptions;
    //Interface
    private ToFormAndDetail listener;

    /*Constructor*/
    //Instance Constructor
    public MyMapFragment newInstanceOf(GoogleMapOptions options, LatLng latLng) {
        //Create Instance
        MyMapFragment myMapFrag = new MyMapFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putParcelable("MapOptions_key", options);
        args.putParcelable("LatLng_key", latLng);
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
            latLon = new LatLng(28.5960, 81.3019);
        } else {
            //Get Current Location Data from custom GoogleMapOptions
            mapOptions = args.getParcelable("MapOptions_key");
            latLon = args.getParcelable("LatLng_key");
            //Menu
            setHasOptionsMenu(true);
        }

        //Initialize Map
        getMapAsync(this);
    }

    /*Map Callback*/
    //Map Ready
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Set Map Long Click Listener
        googleMap.setOnMapLongClickListener(this);

        //Handle Map Configuration
        Bundle args = getArguments();
        if (!handleArguments(args)) {
            //Handle Default Map
            onMapDefaultConfigure(googleMap);
        } else {
            //Handle Map with custom GoogleMapOptions (Loads Markers)
            onMapConfigure(googleMap, mapOptions);
        }

        //Dev
        Log.i(TAG, "onMapReady: " + "MAP READY");
    }

    //Long Click
    @Override
    public void onMapLongClick(LatLng latLng) {
        //MyMapActivity Interface
        listener.toFormLongClick(latLng);
        //Dev
        Log.i(TAG, "onMapLongClick: " + "To Form");
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
                listener.toFormMenu(latLon);
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
        int zoomWorld = 1;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLon, zoomWorld));
        Log.i(TAG, "onMapDefaultConfigure: " + "DEFAULT MAP CONFIGURED");
    }

    //Custom Configuration
    private void onMapConfigure(GoogleMap googleMap, GoogleMapOptions mapOptions) {

        //dEV
        Log.i(TAG, "onMapConfigure: " + "CUSTOM MAP");

        //Clear Map
        googleMap.clear();
        //Set Map Options
        googleMap.setMapType(mapOptions.getMapType());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(mapOptions.getCamera()));
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        //Read ImageLocation Data from Storage to Make Markers
        StorageHelper storageHelper = new StorageHelper();
        ArrayList imageLocations;
        imageLocations = storageHelper.readInternalStorage(getContext());
        if (imageLocations.size() != 0) {
            //Create Markers
            for (int i = 0; i < imageLocations.size(); i++) {
                //Retrieve ImageLocation Object
                ImageLocation imageLocation = (ImageLocation) imageLocations.get(i);
                LatLng imageLocationLatLng = new LatLng(imageLocation.getLat(), imageLocation.getLng());
                Log.i(TAG, "onMapConfigure: " + "LATLNG: " + imageLocationLatLng);
                String imageLocationName = "No Photo !";
                if (imageLocation.getFilePath() != null) {
                    imageLocationName = Uri.parse(imageLocation.getFilePath()).getLastPathSegment();
                }
                googleMap.addMarker(new MarkerOptions()
                        .position(imageLocationLatLng)
                        .title(imageLocationName)
                        .snippet("Lat: " + imageLocation.getLat() + "\n" + "Lng: " + imageLocation.getLng())
                        .draggable(false)
                        .visible(true));
            }
            Log.i(TAG, "onMapConfigure: " + "MARKERS SET");
        }

    }

}
