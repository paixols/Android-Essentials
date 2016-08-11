// Juan Pablo Anaya
// MDF3 - 201608
// MyInfoWindowAdapter

package com.paix.jpam.anayajuan_ce04.map;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    //TAG
    private static final String TAG = "MyInfoWindowAdapter";


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
