// Juan Pablo Anaya
// MDF3 - 201608
// WeatherActivityTask

package com.paix.jpam.anayajuan_ce06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.dataModel.climateAverage.ClimateAverageYear;
import com.paix.jpam.anayajuan_ce06.dataModel.climateAverage.MonthAverage;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.AstronomyInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.CurrentConditionInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.DayForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.ThreeDayWeatherForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.locationInfo.LocationInfo;
import com.paix.jpam.anayajuan_ce06.utilities.NetworkUtility;
import com.paix.jpam.anayajuan_ce06.utilities.ParseWeatherAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherActivityTask extends AsyncTask<Void, Void, Weather> {

    //TAG
    private static final String TAG = "WeatherActivityTask";

    /*Properties*/
    Context mContext;
    OnWeatherApiResult onWeatherApiResult;
    ProgressDialog progressDialog;

    /*Constructor*/
    public WeatherActivityTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Declare Interface
        //Outer Class Interface
        if (mContext instanceof OnWeatherApiResult) {
            onWeatherApiResult = (OnWeatherApiResult) mContext;
        } else {
            throw new IllegalArgumentException("Please Add OnFormMenuSelection Interface");
        }
        //Progress Dialog
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("World Weather Online");
        progressDialog.setMessage("Downloading Weather Data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Weather doInBackground(Void... voids) {

        //Parse Weather Info
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Weather weather = ParseWeatherAPI.
                parseApi(mContext, prefs.getString("CITY_WIDGET", "Orlando"));
        if (weather != null) {
            return weather;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        if (weather != null) {
            //Dev
            Log.i(TAG, "onPostExecute: " + "Data Retrieved");
            onWeatherApiResult.onWeatherApiResult(weather);
            progressDialog.dismiss();
        } else {
            Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show();
        }
    }
}
