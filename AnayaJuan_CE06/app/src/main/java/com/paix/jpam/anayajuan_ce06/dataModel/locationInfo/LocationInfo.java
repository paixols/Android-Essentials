// Juan Pablo Anaya
// MDF3 - 201608
// LocationInfo

package com.paix.jpam.anayajuan_ce06.dataModel.locationInfo;

import java.io.Serializable;

public class LocationInfo implements Serializable {

    private final String cityName;
    private final String localTime;
    private final String utcOffset;
    //Todo add LatLng for location services and query in the API

    public LocationInfo(String cityName, String localTime, String utcOffset) {
        this.cityName = cityName;
        this.localTime = localTime;
        this.utcOffset = utcOffset;
    }

    /*Getters*/
    //City
    public String getCityName() {
        return cityName;
    }

    //LocalTime
    public String getLocalTime() {
        return localTime;
    }

    //UTC Time Offset
    public String getUtcOffset() {
        return utcOffset;
    }
}
