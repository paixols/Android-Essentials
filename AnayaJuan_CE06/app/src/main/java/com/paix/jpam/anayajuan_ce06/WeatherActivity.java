package com.paix.jpam.anayajuan_ce06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paix.jpam.anayajuan_ce06.utilities.NetworkUtility;

public class WeatherActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "WeatherActivity";

    /*Properties*/
    String api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        //Execute Async Task (Weather API)
        WeatherActivityTask weatherActivityTask = new WeatherActivityTask(this);
        weatherActivityTask.execute();

    }
}
