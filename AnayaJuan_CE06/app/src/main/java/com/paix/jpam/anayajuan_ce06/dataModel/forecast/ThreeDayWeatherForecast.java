// Juan Pablo Anaya
// MDF3 - 201608
// ThreeDayWeatherForecast

package com.paix.jpam.anayajuan_ce06.dataModel.forecast;

import java.io.Serializable;

public class ThreeDayWeatherForecast implements Serializable {

    /*Properties*/
    private final DayForecast firstDay;
    private final DayForecast secondDay;
    private final DayForecast thirdDay;

    /*Constructor*/
    public ThreeDayWeatherForecast(DayForecast firstDay, DayForecast secondDay, DayForecast thirdDay) {
        this.firstDay = firstDay;
        this.secondDay = secondDay;
        this.thirdDay = thirdDay;
    }

    /*Getters*/
    //First Day
    public DayForecast getFirstDay() {
        return firstDay;
    }

    //Second Day
    public DayForecast getSecondDay() {
        return secondDay;
    }

    //Third Day
    public DayForecast getThirdDay() {
        return thirdDay;
    }
}
