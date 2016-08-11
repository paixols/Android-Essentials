// Juan Pablo Anaya
// MDF3 - 201608
// ImageLocation

package com.paix.jpam.anayajuan_ce04.utilities;

import java.io.Serializable;

public class ImageLocation implements Serializable {

    /*Properties*/
    private final double lat;
    private final double lng;
    private final String filePath;
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

}
