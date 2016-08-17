// Juan Pablo Anaya
// MDF3 - 201608
// WeatherActivityTask

package com.paix.jpam.anayajuan_ce06;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.AstronomyInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.CurrentConditionInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.DayForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.ThreeDayWeatherForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.locationInfo.LocationInfo;
import com.paix.jpam.anayajuan_ce06.utilities.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivityTask extends AsyncTask<Void, Void, Weather> {

    //TAG
    private static final String TAG = "WeatherActivityTask";

    /*Properties*/
    Context mContext;

    /*Constructor*/
    public WeatherActivityTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Weather doInBackground(Void... voids) {

        if (NetworkUtility.isConnected(mContext)) {
            //Fixed URL
            String apiUrl = "http://api.worldweatheronline.com/premium/v1/weather.ashx?" +
                    "key=%20990c8537d96c4d4fb9d180539161608&q=MexicoCity&num_of_days=3&tp=12&" +
                    "format=json%20&showlocaltime=yes&showmap=yes";
            String api = NetworkUtility.getNetworkData(apiUrl);
            //Data Checkpoint
            if (api.equals("Error")) {
                return null; //No Data
            }
            //Retrieve Weather Data from World Weather Online
            try {
                //Response
                JSONObject response = new JSONObject(api);
                JSONObject data = response.getJSONObject("data"); // Top Level
                //Location Info
                JSONArray request = data.getJSONArray("request");
                JSONObject cityObject = (JSONObject) request.get(0);
                String city = cityObject.getString("query");
                JSONArray timeZone = data.getJSONArray("time_zone");
                JSONObject timeZoneObj = (JSONObject) timeZone.get(0);
                String localTime = timeZoneObj.getString("localtime");
                String utcOffset = timeZoneObj.getString("utcOffset");
                LocationInfo locationInfo = new LocationInfo(city, localTime, utcOffset);//LocationInfo
                //Current Condition Info
                JSONArray currentCondition = data.getJSONArray("current_condition");
                JSONObject currentConditionObject = (JSONObject) currentCondition.get(0);
                String observationTime = currentConditionObject.getString("observation_time");
                String tempC = currentConditionObject.getString("temp_C");
                String tempF = currentConditionObject.getString("temp_F");
                String feelsLikeTempC = currentConditionObject.getString("FeelsLikeC");
                String feelsLikeTempF = currentConditionObject.getString("FeelsLikeF");
                JSONArray iconUrlArray = currentConditionObject.getJSONArray("weatherIconUrl");
                JSONObject iconUrlObject = (JSONObject) iconUrlArray.get(0);
                String iconUrl = iconUrlObject.getString("value");
                JSONArray weatherDescArray = currentConditionObject.getJSONArray("weatherDesc");
                JSONObject weatherDescObject = (JSONObject) weatherDescArray.get(0);
                String weatherDescription = weatherDescObject.getString("value");
                String windSpeedMiles = currentConditionObject.getString("windspeedMiles");
                String windSpeedKmph = currentConditionObject.getString("windspeedKmph");
                String humidity = currentConditionObject.getString("humidity");
                CurrentConditionInfo currentConditionInfo = new CurrentConditionInfo(observationTime,
                        tempC, tempF, feelsLikeTempC, feelsLikeTempF, iconUrl, weatherDescription,
                        windSpeedMiles, windSpeedKmph, humidity);
                //Three Day Forecast
                JSONArray weather = data.getJSONArray("weather");
                DayForecast dayForecastOne = null;
                DayForecast dayForecastTwo = null;
                DayForecast dayForecastThree = null;
                for (int i = 0; i < weather.length(); i++) {
                    JSONObject dayOne = (JSONObject) weather.get(i);
                    String dateOne = dayOne.getString("date");
                    String maxTempCone = dayOne.getString("maxtempC");
                    String maxTempFone = dayOne.getString("maxtempF");
                    String minTempCone = dayOne.getString("mintempC");
                    String minTempFone = dayOne.getString("mintempF");
                    JSONArray hourlyOne = dayOne.getJSONArray("hourly");
                    JSONObject hourlyObjectOne = (JSONObject) hourlyOne.get(0);
                    JSONArray iconUrlOneArray = hourlyObjectOne.getJSONArray("weatherIconUrl");
                    JSONObject weatherIconUrlOneObj = (JSONObject) iconUrlOneArray.get(0);
                    String weatherIconUrlOne = weatherIconUrlOneObj.getString("value");
                    JSONArray weatherDescOneArray = hourlyObjectOne.getJSONArray("weatherDesc");
                    JSONObject weatherDescOneObj = (JSONObject) weatherDescOneArray.get(0);
                    String weatherDescriptionOne = weatherDescOneObj.getString("value");
                    String humidityOne = hourlyObjectOne.getString("humidity");
                    JSONArray astronomyArrayOne = dayOne.getJSONArray("astronomy");
                    JSONObject astronomyObjectOne = (JSONObject) astronomyArrayOne.get(0);
                    String sunriseOne = astronomyObjectOne.getString("sunrise");
                    String sunsetOne = astronomyObjectOne.getString("sunset");
                    String moonriseOne = astronomyObjectOne.getString("moonrise");
                    String moonsetOne = astronomyObjectOne.getString("moonset");
                    AstronomyInfo astronomyInfoOne = new AstronomyInfo(sunriseOne, sunsetOne, moonriseOne, moonsetOne);
                    if(i == 0) {
                        dayForecastOne = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    }else if (i == 1){
                        dayForecastTwo = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    }else if( i ==2) {
                        dayForecastThree = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    }
                }
                ThreeDayWeatherForecast threeDayWeatherForecast = new ThreeDayWeatherForecast(dayForecastOne,dayForecastTwo,dayForecastThree);
//                JSONObject dayOne = (JSONObject) weather.get(0);
//                String dateOne = dayOne.getString("date");
//                String maxTempCone = dayOne.getString("maxtempC");
//                String maxTempFone = dayOne.getString("maxtempF");
//                String minTempCone = dayOne.getString("mintempC");
//                String minTempFone = dayOne.getString("mintempF");
//                JSONArray hourlyOne = dayOne.getJSONArray("hourly");
//                JSONObject hourlyObjectOne = (JSONObject) hourlyOne.get(0);
//                JSONArray iconUrlOneArray = hourlyObjectOne.getJSONArray("weatherIconUrl");
//                JSONObject weatherIconUrlOneObj = (JSONObject) iconUrlOneArray.get(0);
//                String weatherIconUrlOne = weatherIconUrlOneObj.getString("value");
//                JSONArray weatherDescOneArray = hourlyObjectOne.getJSONArray("weatherDesc");
//                JSONObject weatherDescOneObj = (JSONObject) weatherDescOneArray.get(0);
//                String weatherDescriptionOne = weatherDescOneObj.getString("value");
//                String humidityOne = hourlyObjectOne.getString("humidity");
//                JSONArray astronomyArrayOne = dayOne.getJSONArray("astronomy");
//                JSONObject astronomyObjectOne = (JSONObject) astronomyArrayOne.get(0);
//                String sunriseOne = astronomyObjectOne.getString("sunrise");
//                String sunsetOne = astronomyObjectOne.getString("sunset");
//                String moonriseOne = astronomyObjectOne.getString("moonrise");
//                String moonsetOne = astronomyObjectOne.getString("moonset");
//                AstronomyInfo astronomyInfoOne = new AstronomyInfo(sunriseOne, sunsetOne, moonriseOne, moonsetOne);
//                DayForecast dayForecastOne = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
//                        maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
//
//
//                JSONObject dayTwo = (JSONObject) weather.get(1);
//                JSONObject dayThree = (JSONObject) weather.get(2);

                //
                //Dev
                //Location Info
                Log.i(TAG, "doInBackground: " + "City: " + city + "/" + "LocalTime: " + localTime +
                        "Utc_Offset: " + utcOffset);
                //Current Condition info
                Log.i(TAG, "doInBackground: " + "Observation Time: " + observationTime + " / TempC: " +
                        tempC + " / TempF:" + tempF + " / Feels Like TempC: " + feelsLikeTempC +
                        " / Feels Like TempF: " + tempF + " / Icon Url: " + iconUrl + " / Weather Desc: " +
                        weatherDescription + " / Wind Speed M: " + windSpeedMiles + " / Wind Speed Kmph: " +
                        windSpeedKmph + " / Humidity: " + humidity);
                //Day Forecast One
                Log.i(TAG, "doInBackground: " + "Day Forecast One: " + "/ Date: " + dayForecastOne.getDate() +
                        "Astronomy Info: " + "-Moonrise: " + dayForecastOne.getAstronomyInfo().getMoonrise() +
                        " - Moonset: " + dayForecastOne.getAstronomyInfo().getMoonset() + " - Sunrise: " +
                        dayForecastOne.getAstronomyInfo().getSunrise() + " - Sunset: " + dayForecastOne.getAstronomyInfo().getSunset() +
                        " / Max Temp C: " + dayForecastOne.getMaxTempC() + " /  Min Temp C: " + dayForecastOne.getMinTempC() +
                        " / Max Temp F: " + dayForecastOne.getMaxTempF() + " / Min Temp F: " + dayForecastOne.getMinTempF() +
                        " / Weather Icon Url: " + dayForecastOne.getWeatherIconUrl() + " / Weather Desc: " + dayForecastOne.getWeatherDescription() +
                        " / Humidity: " + humidity);
                //Weather Forecast
                Log.i(TAG, "doInBackground: " + "/ Weather Forecast 1 : " + threeDayWeatherForecast.getFirstDay().getDate() +
                " / Weather Forecast 2: " + threeDayWeatherForecast.getSecondDay().getDate() + " / Weather Forecast 3: " +
                threeDayWeatherForecast.getThirdDay().getDate());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        super.onPostExecute(weather);
        if (weather != null) {
            //Dev
            Log.i(TAG, "onPostExecute: " + "Data Retrieved");
        } else {
            Toast.makeText(mContext, "No Data Available", Toast.LENGTH_SHORT).show();
        }
    }
}
