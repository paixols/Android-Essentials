// Juan Pablo Anaya
// MDF3 - 201608
// Widget Helper

package com.paix.jpam.anayajuan_ce06.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.widget.preferences.WeatherWidgetPreferencesAct;

public class WidgetHelper {

    //TAG
    private static final String TAG = "WidgetHelper";

    /*Widget Update*/
    public static void updateWidget(Context context, AppWidgetManager manager, int widgetId) {
        //Remote Views
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        //Config Activity (Button Intent)
        Intent configIntent = new Intent(context, WeatherWidgetPreferencesAct.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ImageButton_WidgetConfig, pendingIntent);

        //Dev
        Log.i(TAG, "updateWidget: " + "UPDATE_WIDGET");

        //Update Widget
        manager.updateAppWidget(widgetId,remoteViews);
    }

    //TODO Update Widget Theme and Info with Saved Weather Information

}
