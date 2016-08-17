// Juan Pablo Anaya
// MDF3 - 201608
// ClimateAverageYear

package com.paix.jpam.anayajuan_ce06.dataModel.climateAverage;

import java.io.Serializable;

public class ClimateAverageYear implements Serializable {

    /*Properties*/
    MonthAverage january;
    MonthAverage february;
    MonthAverage march;
    MonthAverage april;
    MonthAverage may;
    MonthAverage june;
    MonthAverage july;
    MonthAverage august;
    MonthAverage september;
    MonthAverage october;
    MonthAverage november;
    MonthAverage december;

    /*Constructor*/
    public ClimateAverageYear(MonthAverage january, MonthAverage february, MonthAverage march,
                              MonthAverage april, MonthAverage may, MonthAverage june,
                              MonthAverage july, MonthAverage august, MonthAverage september,
                              MonthAverage october, MonthAverage november, MonthAverage december) {
        this.january = january;
        this.february = february;
        this.march = march;
        this.april = april;
        this.may = may;
        this.june = june;
        this.july = july;
        this.august = august;
        this.september = september;
        this.october = october;
        this.november = november;
        this.december = december;
    }

    /*Getters*/
    //January
    public MonthAverage getJanuary() {
        return january;
    }

    //February
    public MonthAverage getFebruary() {
        return february;
    }

    //March
    public MonthAverage getMarch() {
        return march;
    }

    //April
    public MonthAverage getApril() {
        return april;
    }

    //May
    public MonthAverage getMay() {
        return may;
    }

    //June
    public MonthAverage getJune() {
        return june;
    }

    //July
    public MonthAverage getJuly() {
        return july;
    }

    //August
    public MonthAverage getAugust() {
        return august;
    }

    //September
    public MonthAverage getSeptember() {
        return september;
    }

    //October
    public MonthAverage getOctober() {
        return october;
    }

    //November
    public MonthAverage getNovember() {
        return november;
    }

    //December
    public MonthAverage getDecember() {
        return december;
    }
}
