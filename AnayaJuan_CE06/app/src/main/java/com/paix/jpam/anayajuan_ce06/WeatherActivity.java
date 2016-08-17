// Juan Pablo Anaya
// MDF3 - 201608
// WeatherActivity

package com.paix.jpam.anayajuan_ce06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.fragments.CurrentConditionFragment;
import com.paix.jpam.anayajuan_ce06.fragments.LocationFragment;

public class WeatherActivity extends AppCompatActivity implements OnWeatherApiResult {

    //TAG
    private static final String TAG = "WeatherActivity";

    /*LifeCycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //Execute Async Task (Weather API)
        WeatherActivityTask weatherActivityTask = new WeatherActivityTask(this);
        weatherActivityTask.execute();

    }

    /*Weather API Result*/
    @Override
    public void onWeatherApiResult(Weather weather) {
        //Dev (City)
        Log.i(TAG, "onWeatherApiResult: " + weather.getLocationInfo().getCityName());
        /*Set Fragments*/
        //Location
        LocationFragment locationFrag = new LocationFragment().newInstanceOf(this,weather);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_City_FragHolder, locationFrag).commit();
        //Current Condition
        CurrentConditionFragment currentConditionFrag = new CurrentConditionFragment().newInstanceOf(this,weather);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_CurrentCondition_FragHolder,currentConditionFrag).commit();
        //Weather Forecast


    }
}
