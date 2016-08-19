// Juan Pablo Anaya
// MDF3 - 201608
// WeatherWidgetPreferencesAct

package com.paix.jpam.anayajuan_ce06.widget.preferences;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.services.WeatherWidgetService;

public class WeatherWidgetPreferencesAct extends AppCompatActivity implements OnSelectedPreferences {

    //TAG
    private static final String TAG = "WeatherWidgetPrefAct";

    /*Properties*/
    int widgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_widget_preferences);
        /*Check for a Valid Widget ID*/
        Intent intent = getIntent();
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent); //Removes the Widget from the HomeScreen
            finish();
        }
//        //Dev
//        Log.i(TAG, "onCreate: " + "Widget Pref Activity");
        //Set Preferences Fragment
        WeatherWidgetPreferencesFrag prefFrag = new WeatherWidgetPreferencesFrag();
        getFragmentManager().beginTransaction().
                replace(R.id.FrameLayout_Preferences_FragHolder, prefFrag).commit();

    }


    @Override
    public void selectedCityAndTheme(String city, String theme) {
//        //Dev
//        Log.i(TAG, "selectedCityAndTheme: " + "City: " + city + "/ Theme: " + theme);
//        Log.i(TAG, "selectedCityAndTheme: " + "Widget ID: " + widgetId);
        Intent serviceIntent = new Intent(getApplicationContext(), WeatherWidgetService.class);
        serviceIntent.putExtra(getString(R.string.widget_id), widgetId);
        getApplicationContext().startService(serviceIntent);
        //Finish Preferences Activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultIntent); //Sticks the Widget to the HomeScreen
        finish();
    }
}
