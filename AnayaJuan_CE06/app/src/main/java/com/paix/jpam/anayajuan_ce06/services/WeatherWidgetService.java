// Juan Pablo Anaya
// MDF3 - 201608
// WeatherWidgetService

package com.paix.jpam.anayajuan_ce06.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.utilities.ParseWeatherAPI;
import com.paix.jpam.anayajuan_ce06.widget.WidgetHelper;

public class WeatherWidgetService extends IntentService {


    //Tag
    private static final String TAG = "WeatherWidgetService";

    /*Constructor*/
    //Required
    public WeatherWidgetService() {
        super("Weather Widget Service API");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Get Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        Log.i(TAG, "onHandleIntent: " + prefs.getString("CITY_WIDGET", "Orlando"));
//        Log.i(TAG, "onHandleIntent: " + prefs.getString("THEME_WIDGET", "Light"));
        int widgetId = intent.getIntExtra(getString(R.string.widget_id), AppWidgetManager.INVALID_APPWIDGET_ID);
        //Parse Weather Data
        Weather weather = ParseWeatherAPI.parseApi(this, prefs.getString("CITY_WIDGET", "Orlando"));
        if (weather != null) {
            Log.i(TAG, "onHandleIntent: " + weather.getLocationInfo().getCityName());
            //Update Widget
            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                AppWidgetManager mgr = AppWidgetManager.getInstance(getApplicationContext());
                WidgetHelper.updateWidget(getApplicationContext(), mgr, widgetId);
                Log.i(TAG, "onHandleIntent: " + widgetId);
            }
        }
    }
}
