// Juan Pablo Anaya
// MDF3 - 201608
// MonthAverage

package com.paix.jpam.anayajuan_ce06.dataModel.climateAverage;

import java.io.Serializable;

public class MonthAverage implements Serializable {

    private final String absMaxTemp_C;
    private final String absMinTemp_F;

    /*Constructor*/
    public MonthAverage(String absMaxTemp_C, String absMinTemp_F) {
        this.absMaxTemp_C = absMaxTemp_C;
        this.absMinTemp_F = absMinTemp_F;
    }

    /*Getters*/

    private String getAbsMaxTemp_C() {
        return absMaxTemp_C;
    }

    private String getAbsMaxTemp_F() {
        return absMinTemp_F;
    }

    public String getAbsMaxBoth() {
        return getAbsMaxTemp_C() + " C / " + getAbsMaxTemp_F() + " F";
    }
}
