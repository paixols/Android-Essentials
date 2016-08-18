// Juan Pablo Anaya
// MDF3 - 201608
// MonthAverage

package com.paix.jpam.anayajuan_ce06.dataModel.climateAverage;

import java.io.Serializable;

public class MonthAverage implements Serializable {

    /*Properties*/
    String index;
    String name;
    String avgMinTemp_C;
    String avgMinTemp_F;
    String absMaxTemp_C;
    String absMinTemp_F;

    /*Constructor*/
    public MonthAverage(String index, String name, String avgMinTemp_C, String avgMinTemp_F, String absMaxTemp_C, String absMinTemp_F) {
        this.index = index;
        this.name = name;
        this.avgMinTemp_C = avgMinTemp_C;
        this.avgMinTemp_F = avgMinTemp_F;
        this.absMaxTemp_C = absMaxTemp_C;
        this.absMinTemp_F = absMinTemp_F;
    }

    /*Getters*/
    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getAvgMinTemp_C() {
        return avgMinTemp_C + " C";
    }

    public String getAvgMinTemp_F() {
        return avgMinTemp_F;
    }

    public String getAbsMaxTemp_C() {
        return absMaxTemp_C;
    }

    public String getAbsMinTemp_F() {
        return absMinTemp_F;
    }

    public String getAbsMaxBoth(){return absMaxTemp_C + " C / " + getAbsMinTemp_F() + " F";}
}
