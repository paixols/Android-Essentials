// Juan Pablo Anaya
// MDF3 - 201608
// DayForecast

package com.paix.jpam.anayajuan_ce06.dataModel.forecast;

import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.AstronomyInfo;

import java.io.Serializable;

public class DayForecast implements Serializable {

    /*Properties*/
    String date;
    AstronomyInfo astronomyInfo; //
    String maxTempC;
    String minTempC;
    String maxTempF;
    String minTempF;
    String weatherIconUrl;
    String weatherDescription;
    String humidity; // %

    /*Constructor*/
    public DayForecast(String date, AstronomyInfo astronomyInfo, String maxTempC,
                       String minTempC, String maxTempF, String minTempF,
                       String weatherIconUrl, String weatherDescription,
                       String humidity) {
        this.date = date;
        this.astronomyInfo = astronomyInfo;
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.maxTempF = maxTempF;
        this.minTempF = minTempF;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDescription = weatherDescription;
        this.humidity = humidity;

    }

    /*Getters*/
    //Date
    public String getDate() {
        return date;
    }

    //Astronomy
    public AstronomyInfo getAstronomyInfo() {
        return astronomyInfo;
    }

    //Max Temp C
    public String getMaxTempC() {
        return "Max: " + maxTempC;
    }

    //Min Temp C
    public String getMinTempC() {
        return "Min: " + minTempC;
    }

    //Max Temp F
    public String getMaxTempF() {
        return "Max: " + maxTempF;
    }

    //Min Temp F
    public String getMinTempF() {
        return "Min: " + minTempF;
    }

    //Weather Icon
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    //Weather Description
    public String getWeatherDescription() {
        return weatherDescription;
    }

    //Humidity
    public String getHumidity() {
        return humidity + " %";
    }

}
