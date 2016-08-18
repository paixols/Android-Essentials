// Juan Pablo Anaya
// MDF3 - 201608
// WeatherWidgetProvider

package com.paix.jpam.anayajuan_ce06.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class WeatherWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Loop through the widgets
        for (int i = 0; i < appWidgetIds.length; i++) {
            WidgetHelper.updateWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }
}
