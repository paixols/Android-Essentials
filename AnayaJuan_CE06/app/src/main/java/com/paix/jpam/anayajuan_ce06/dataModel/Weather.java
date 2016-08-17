// Juan Pablo Anaya
// MDF3 - 201608
// Weather

package com.paix.jpam.anayajuan_ce06.dataModel;

import com.paix.jpam.anayajuan_ce06.dataModel.climateAverage.ClimateAverageYear;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.CurrentConditionInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.ThreeDayWeatherForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.locationInfo.LocationInfo;

import java.io.Serializable;

public class Weather implements Serializable {

    /*Properties*/
    LocationInfo locationInfo;
    CurrentConditionInfo currentConditionInfo;
    ThreeDayWeatherForecast threeDayWeatherForecast;
    ClimateAverageYear climateAverageYear;

    /*Constructor*/
    public Weather(LocationInfo locationInfo, CurrentConditionInfo currentConditionInfo,
                   ThreeDayWeatherForecast threeDayWeatherForecast, ClimateAverageYear climateAverageYear) {
        this.locationInfo = locationInfo;
        this.currentConditionInfo = currentConditionInfo;
        this.threeDayWeatherForecast = threeDayWeatherForecast;
        this.climateAverageYear = climateAverageYear;
    }

    /*Getters*/
    //Location Info
    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    //Current Weather Condition Info
    public CurrentConditionInfo getCurrentConditionInfo() {
        return currentConditionInfo;
    }

    //3 Day Weather Forecast Info
    public ThreeDayWeatherForecast getThreeDayWeatherForecast() {
        return threeDayWeatherForecast;
    }

    public ClimateAverageYear getClimateAverageYear() {
        return climateAverageYear;
    }
}
