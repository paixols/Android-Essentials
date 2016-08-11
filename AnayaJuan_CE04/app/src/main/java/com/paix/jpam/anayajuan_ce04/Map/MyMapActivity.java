// Juan Pablo Anaya
// MDF3 - 201608
// MyMapActivity

package com.paix.jpam.anayajuan_ce04.map;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.paix.jpam.anayajuan_ce04.detail.DetailActivity;
import com.paix.jpam.anayajuan_ce04.form.FormActivity;
import com.paix.jpam.anayajuan_ce04.R;

public class MyMapActivity extends AppCompatActivity implements ToFormAndDetail,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //TAG
    private static final String TAG = "MyMapActivity";

    /*Permissions*/
    //Location
    private static final String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int REQUEST_LOCATION = 0x0001;

    //Broadcast Receiver (Update UI)
    public static final String UPDATE_MAP = "com.paix.jpam.anayajuan_ce04.UPDATE_MAP";
    private UpdateReceiver mReceiver;


    /*Properties*/
    //Google Api Client
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LatLng mLastLatLng;


    //UI
    private View mLayout;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        //UI
        mLayout = findViewById(R.id.LinearLayout_MapActivity);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

        //Set Map Fragment
        MyMapFragment myMapFragment = new MyMapFragment();
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Map_FragHolder,
                myMapFragment, MyMapFragment.TAG).commit();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register Broadcast Receivers
        mReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_MAP);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Un-register Broadcast Receivers
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /*Map Frag Form And Detail Interface*/
    @Override
    public void toFormMenu(LatLng latLng) {
        Intent formIntent = new Intent(this, FormActivity.class);
        formIntent.putExtra("LatLng_value", latLng);
        startActivity(formIntent);
    }

    @Override
    public void toFormLongClick(LatLng latLng) {
        Intent formIntent = new Intent(this, FormActivity.class);
        formIntent.putExtra("LatLng_value", latLng);
        startActivity(formIntent);
    }

    //Detail
    @Override
    public void toDetail(Marker marker) {
        //TODO transition to Detail Activity
        Log.i(TAG, "toDetail: " + "Marker: " + marker.getTitle());
        Intent detailIntent = new Intent(MyMapActivity.this, DetailActivity.class);
        detailIntent.putExtra("LatLng_key", marker.getPosition());
        detailIntent.putExtra("PhotoName_key", marker.getTitle());
        startActivity(detailIntent);
    }

    /*Google API Client*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Request Permissions
        if (requestPermissions()) {
            requestLocation();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        //Dev
        Log.i(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Dev
        Log.i(TAG, "onConnectionFailed: ");
    }

    /*Permissions*/
    private Boolean requestPermissions() {
        //Check SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //> API 23 , Request Permissions at runtime
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                //If permissions are not granted (Rationale?)
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Re-direct to permissions !
                    Snackbar.make(mLayout, "I'm a Map App , I really need your location!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MyMapActivity.this, PERMISSION_LOCATION, REQUEST_LOCATION);
                                }
                            }).show();
                    return false;
                } else {

                    //Request Permissions
                    ActivityCompat.requestPermissions(MyMapActivity.this, PERMISSION_LOCATION, REQUEST_LOCATION);
                    return false;
                }
            } else {
                //If permissions are granted
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //LOCATION PERMISSIONS
        switch (requestCode) {
            case REQUEST_LOCATION:
                //If the request is cancelled the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Let the user know the permissions have been granted
                    Snackbar.make(mLayout, "Location permissions granted !", Snackbar.LENGTH_SHORT).show();
                    //Request Location
                    requestLocation();
                } else {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
        }
    }

    /*Location*/
    //Request Location
    private void requestLocation() {
        /*LOCATION*/
        LocationRequest mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Last Known Location /*Location Object returned may be null in some rare cases*/
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation == null) {
            //Check Location Permissions
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION..
                //Request Location
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                //Request Permissions for Location
                requestPermissions();
            }
        } else {

            //Build LatLon Object
            mLastLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            //Build Google Maps Options Object
            GoogleMapOptions mapOptions = getGoogleMapOptions();
            //Set Fragment
            setMapFragment(mapOptions, mLastLatLng);
        }


    }

    //Location Changed
    @Override
    public void onLocationChanged(Location location) {
        /*Location Changes*/
        mLastLocation = location;
        mLastLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        //Build Google Maps Options Object
        GoogleMapOptions mapOptions = getGoogleMapOptions();
        setMapFragment(mapOptions, mLastLatLng);
        //Dev
        Log.i(TAG, "onLocationChanged:  Lat: " + mLastLatLng.latitude + "  Lon: " + mLastLatLng.longitude);
    }

    /*Google Map Options Builder*/
    @NonNull
    private GoogleMapOptions getGoogleMapOptions() {
        GoogleMapOptions mapOptions = new GoogleMapOptions();
        mapOptions.compassEnabled(true)
                .camera(CameraPosition.fromLatLngZoom(mLastLatLng, 10))
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(true)
                .mapToolbarEnabled(true);
        return mapOptions;
    }

    /*Set MyMap Fragment*/
    private void setMapFragment(GoogleMapOptions mapOptions, LatLng latLng) {
        MyMapFragment myMapFragment = new MyMapFragment().newInstanceOf(mapOptions, latLng);
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Map_FragHolder,
                myMapFragment, MyMapFragment.TAG).commit();
    }

    /*Broadcast Receiver for UI - Updates*/
    private class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_MAP)) {
                //Set Map Fragment
                GoogleMapOptions mapOptions = getGoogleMapOptions();
                setMapFragment(mapOptions, mLastLatLng);
                //Dev
                Log.i(TAG, "onReceive: " + "UI-UPDATED");
            }
        }
    }
}
