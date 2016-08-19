// Juan Pablo Anaya
// MDF3 - 201608
// WeatherWidgetPreferencesFrag

package com.paix.jpam.anayajuan_ce06.widget.preferences;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.paix.jpam.anayajuan_ce06.R;

public class WeatherWidgetPreferencesFrag extends PreferenceFragment {

    //TAG
    private static final String TAG = "WeatherWidgetPrefFrag";

    /*Properties*/
    private OnSelectedPreferences listener;

    /*LifeCycle*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Outer Class Interface
        if (context instanceof OnSelectedPreferences) {
            listener = (OnSelectedPreferences) context;
        } else {
            throw new IllegalArgumentException(("Please Add Interface"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Fragment Layout
        addPreferencesFromResource(R.xml.widget_preferences);
        //Menu
        setHasOptionsMenu(true);
        //Dev
        Log.i(TAG, "onCreate: " + "Widget Pref Fragment");
    }

    /*Menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weather_activity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_reloadApi) {
            listener.selectedCityAndTheme();
            return true;
        }
        return false;
    }

}
