// Juan Pablo Anaya
// MDF3 - 201608
// ToFormAndDetail

package com.paix.jpam.anayajuan_ce04.Map;

import com.google.android.gms.maps.model.LatLng;

public interface ToFormAndDetail {

    //To Form Interface when Menu Button is Pressed
    void toFormMenu(LatLng latLng);

    //To Form Interface when Map is Long Clicked
    void toFormLongClick(LatLng latLng);

    //To Detail Interface
    void toDetail();
}
