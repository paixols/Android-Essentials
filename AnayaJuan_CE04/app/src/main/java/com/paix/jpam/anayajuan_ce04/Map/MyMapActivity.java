// Juan Pablo Anaya
// MDF3 - 201608
// MyMapActivity

package com.paix.jpam.anayajuan_ce04.Map;

import android.Manifest;
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
import com.google.android.gms.maps.model.LatLng;
import com.paix.jpam.anayajuan_ce04.R;

public class MyMapActivity extends AppCompatActivity implements ToFormAndDetail,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //TAG
    private static final String TAG = "MyMapActivity";

    /*Permissions*/
    //Location
    private static final String[] PERMISSION_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int REQUEST_LOCATION = 0x0001;


    /*Properties*/
    //Google Api Client
    GoogleApiClient mGoogleApiClient;
    //Location
    LocationRequest mLocationRequest;
    Location mLocation;
    double mLatitude;
    double mLongitude;

    //UI
    private View mLayout;

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

            //UI
            mLayout = findViewById(R.id.LinearLayout_MapActivity);
        }

        //Set Map Fragment
        MyMapFragment myMapFragment = new MyMapFragment();
        getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Map_FragHolder, myMapFragment).commit();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();


    }

    /*Map Frag Form And Detail Interface*/
    //Form
    @Override
    public void toForm() {
        //TODO transition to Form Activity
    }

    //Detail
    @Override
    public void toDetail() {
        //TODO transition to Detail Activity
    }

    /*Google API Client*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Request Permissions
        requestPermissions();
        //Request Location
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /*Permissions*/
    private void requestPermissions() {
        //Check SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //> API 23 , Request Permissions at runtime
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //If permissions are not granted (Rationale?)
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Re-direct to permissions !
                    Snackbar.make(mLayout, "I'm a Map App , I really need your location!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MyMapActivity.this, PERMISSION_LOCATION, REQUEST_LOCATION);
                                }
                            }).show();

                } else {

                    //Request Permissions
                    ActivityCompat.requestPermissions(MyMapActivity.this, PERMISSION_LOCATION, REQUEST_LOCATION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //LOCATION PERMISSIONS
        switch (requestCode) {
            case REQUEST_LOCATION:

                //If the request is cancelled the result arrays are empty
                if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) { //Coarse Location

                    //Let the user know the permissions have been granted
                    Snackbar.make(mLayout, "Location permissions granted !", Snackbar.LENGTH_SHORT).show();
                    //Request Location
                    requestLocation();

                } else {

                    //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                //return;
        }
    }

    /*Location*/
    //Request Location
    private void requestLocation() {
        /*LOCATION*/
        //Last known location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //
        if (mGoogleApiClient.isConnected()) {

            if (mLocation != null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mLatitude = mLocation.getLatitude();
                mLongitude = mLocation.getLongitude();

                Log.i(TAG, "requestPermissions: " + "Lat: " + mLatitude + "   Lon: " + mLongitude);
            }
//            LatLng currentLatLng = new LatLng(mLatitude, mLongitude);
//            MyMapFragment myMapFrag = new MyMapFragment().newInstanceOf(currentLatLng);
//            getFragmentManager().beginTransaction().replace(R.id.FrameLayout_Map_FragHolder, myMapFrag).commit();
        }else{

        }
    }

    //Location Changed
    @Override
    public void onLocationChanged(Location location) {

    }
}
