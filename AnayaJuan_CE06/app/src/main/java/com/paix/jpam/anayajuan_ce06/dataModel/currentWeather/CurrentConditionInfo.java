// Juan Pablo Anaya
// MDF3 - 201608
// CurrentConditionInfo


package com.paix.jpam.anayajuan_ce06.dataModel.currentWeather;

import java.io.Serializable;

public class CurrentConditionInfo implements Serializable {

    /*Properties*/
    String observationTime;
    String tempC;
    String tempF;
    String feelsLikeTempC;
    String feelsLikeTempF;
    String weatherIconUrl;
    String weatherDescription;
    String windSpeedMiles;
    String windSpeedKm;
    String humidity; // %
    /*Constructor*/
    public CurrentConditionInfo(String observationTime, String tempC, String tempF,
                                String feelsLikeTempC, String feelsLikeTempF, String weatherIconUrl,
                                String weatherDescription, String windSpeedMiles, String windSpeedKm,
                                String humidity) {
        this.observationTime = observationTime;
        this.tempC = tempC;
        this.tempF = tempF;
        this.feelsLikeTempC = feelsLikeTempC;
        this.feelsLikeTempF = feelsLikeTempF;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDescription = weatherDescription;
        this.windSpeedMiles = windSpeedMiles;
        this.windSpeedKm = windSpeedKm;
        this.humidity = humidity;
    }
    /*Getters*/
    //Observation Time
    public String getObservationTime() {
        return observationTime;
    }
    //Temperature in Celsius
    public String getTempC() {
        return tempC;
    }
    //Temperature in Fahrenheit
    public String getTempF() {
        return tempF;
    }
    //Temperature feels like in Celsius
    public String getFeelsLikeTempC() {
        return feelsLikeTempC;
    }
    //Temperature feels like in Fahrenheit
    public String getFeelsLikeTempF() {
        return feelsLikeTempF;
    }
    //Weather Icon
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }
    //Current Weather Description
    public String getWeatherDescription() {
        return weatherDescription;
    }
    //Wind Speed in Miles
    public String getWindSpeedMiles() {
        return windSpeedMiles;
    }
    //Wind Speed in KM
    public String getWindSpeedKm() {
        return windSpeedKm;
    }
    //Humidity %
    public String getHumidity() {
        return humidity + " %";
    }
}
