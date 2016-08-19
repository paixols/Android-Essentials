// Juan Pablo Anaya
// MDF3 - 201608
// AstronomyInfo

package com.paix.jpam.anayajuan_ce06.dataModel.currentWeather;

import java.io.Serializable;

public class AstronomyInfo implements Serializable {

    /*Properties*/
    private final String sunrise;
    private final String sunset;
    private final String moonrise;
    private final String moonset;

    /*Constructor*/
    public AstronomyInfo(String sunrise, String sunset, String moonrise, String moonset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
    }

    /*Getters*/
    //Sunrise Time
    public String getSunrise() {
        return sunrise;
    }

    //Sunset Time
    public String getSunset() {
        return sunset;
    }

    //Moonrise
    public String getMoonrise() {
        return moonrise;
    }

    //Moonset
    public String getMoonset() {
        return moonset;
    }
}
