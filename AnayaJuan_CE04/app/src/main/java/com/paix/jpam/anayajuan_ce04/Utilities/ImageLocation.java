// Juan Pablo Anaya
// MDF3 - 201608
// ImageLocation

package com.paix.jpam.anayajuan_ce04.Utilities;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class ImageLocation implements Serializable {

    /*Properties*/
    double lat;
    double lng;
    String filePath;
    /*Constructor*/
    public ImageLocation(double lat, double lng, String filePath) {
        this.lat = lat;
        this.lng = lng;
        this.filePath = filePath;
    }
    /*Getters*/
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getFilePath() {
        return filePath;
    }
    /*Setters*/
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
