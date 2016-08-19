// Juan Pablo Anaya
// MDF3 - 201608
// ParseWeatherApi

package com.paix.jpam.anayajuan_ce06.utilities;

import android.content.Context;
import android.util.Log;

import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.paix.jpam.anayajuan_ce06.dataModel.climateAverage.ClimateAverageYear;
import com.paix.jpam.anayajuan_ce06.dataModel.climateAverage.MonthAverage;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.AstronomyInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.currentWeather.CurrentConditionInfo;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.DayForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.forecast.ThreeDayWeatherForecast;
import com.paix.jpam.anayajuan_ce06.dataModel.locationInfo.LocationInfo;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ParseWeatherAPI {

    //TAG
    private static final String TAG = "ParseWeatherAPI";


    public static Weather parseApi(Context mContext, String chosenCity) {

        //Weather Object (Return value)
        Weather weatherInfo = null;
        byte[] currentWeatherIcon = null;
        if (NetworkUtility.isConnected(mContext)) {
            //Fixed URL
            String apiUrl = "https://api.worldweatheronline.com/premium/v1/weather.ashx" +
                    "?key=990c8537d96c4d4fb9d180539161608&q=" + chosenCity + "&format=json&num_of_days=3" +
                    "&tp=12&showlocaltime=yes&showmap=yes";
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
                    if (i == 0) {
                        dayForecastOne = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    } else if (i == 1) {
                        dayForecastTwo = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    } else if (i == 2) {
                        dayForecastThree = new DayForecast(dateOne, astronomyInfoOne, maxTempCone, minTempCone,
                                maxTempFone, minTempFone, weatherIconUrlOne, weatherDescriptionOne, humidityOne);
                    }
                }
                ThreeDayWeatherForecast threeDayWeatherForecast = new ThreeDayWeatherForecast(dayForecastOne, dayForecastTwo, dayForecastThree);
                //Climate Average Year
                JSONArray climateAveragesArray = data.getJSONArray("ClimateAverages");
                JSONObject climateAveragesObj = (JSONObject) climateAveragesArray.getJSONObject(0);
                JSONArray monthArray = climateAveragesObj.getJSONArray("month");
                ArrayList<MonthAverage> months = new ArrayList<>();
                for (int i = 0; i < monthArray.length(); i++) {
                    JSONObject month = (JSONObject) monthArray.get(i);
                    String index = month.getString("index");
                    String name = month.getString("name");
                    String avgMinTempC = month.getString("avgMinTemp");
                    String avgMinTempF = month.getString("avgMinTemp_F");
                    String absMaxTempC = month.getString("absMaxTemp");
                    String absMaxTempF = month.getString("absMaxTemp_F");
                    MonthAverage monthAverage = new MonthAverage(index, name, avgMinTempC, avgMinTempF,
                            absMaxTempC, absMaxTempF);
                    months.add(monthAverage);
                }
                ClimateAverageYear climateAverageYear = new ClimateAverageYear(months.get(0), months.get(1),
                        months.get(2), months.get(3), months.get(4), months.get(5), months.get(6), months.get(7),
                        months.get(8), months.get(9), months.get(10), months.get(11));
                //Weather Object
                weatherInfo = new Weather(locationInfo, currentConditionInfo, threeDayWeatherForecast, climateAverageYear);
                //Download Icon for Widget
                currentWeatherIcon = getImageAsByteArray(weatherInfo.getCurrentConditionInfo().getWeatherIconUrl());
//                //Dev
//                //Location Info
//                Log.i(TAG, "doInBackground: " + "City: " + city + "/" + "LocalTime: " + localTime +
//                        "Utc_Offset: " + utcOffset);
//                //Current Condition info
//                Log.i(TAG, "doInBackground: " + "Observation Time: " + observationTime + " / TempC: " +
//                        tempC + " / TempF:" + tempF + " / Feels Like TempC: " + feelsLikeTempC +
//                        " / Feels Like TempF: " + tempF + " / Icon Url: " + iconUrl + " / Weather Desc: " +
//                        weatherDescription + " / Wind Speed M: " + windSpeedMiles + " / Wind Speed Kmph: " +
//                        windSpeedKmph + " / Humidity: " + humidity);
//                //Day Forecast One
//                Log.i(TAG, "doInBackground: " + "Day Forecast One: " + "/ Date: " + dayForecastOne.getDate() +
//                        "Astronomy Info: " + "-Moonrise: " + dayForecastOne.getAstronomyInfo().getMoonrise() +
//                        " - Moonset: " + dayForecastOne.getAstronomyInfo().getMoonset() + " - Sunrise: " +
//                        dayForecastOne.getAstronomyInfo().getSunrise() + " - Sunset: " + dayForecastOne.getAstronomyInfo().getSunset() +
//                        " / Max Temp C: " + dayForecastOne.getMaxTempC() + " /  Min Temp C: " + dayForecastOne.getMinTempC() +
//                        " / Max Temp F: " + dayForecastOne.getMaxTempF() + " / Min Temp F: " + dayForecastOne.getMinTempF() +
//                        " / Weather Icon Url: " + dayForecastOne.getWeatherIconUrl() + " / Weather Desc: " + dayForecastOne.getWeatherDescription() +
//                        " / Humidity: " + humidity);
//                //Weather Forecast
//                Log.i(TAG, "doInBackground: " + "/ Weather Forecast 1 : " + threeDayWeatherForecast.getFirstDay().getDate() +
//                        " / Weather Forecast 2: " + threeDayWeatherForecast.getSecondDay().getDate() + " / Weather Forecast 3: " +
//                        threeDayWeatherForecast.getThirdDay().getDate());
//                //Weather Info
//                Log.i(TAG, "doInBackground: " + " Weather Info: " + weatherInfo.getClimateAverageYear().getJanuary().getName());
//                //Dev
//                Log.i(TAG, "parseApi: " + currentWeatherIcon.length);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Weather Information Successfully downloaded
        if (weatherInfo != null) {
            /*Save Data Locally*/
            //Save Weather Object
            StorageHelper.save(mContext, weatherInfo);
            //Save Weather Icons
            if (currentWeatherIcon != null) {
                StorageHelper.saveWidgetIcon(mContext, currentWeatherIcon);
            }
            return weatherInfo;
        }
        return null;
    }

    //Download Image Byte Array
    public static byte[] getImageAsByteArray(String _url) {

        try {
            //Get URL
            URL url = new URL(_url);
            //Make a connection Object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Check for successful response code
            if (connection.getResponseCode() != 200) {
                return new byte[0];
            }
            //If Network Connection is successful
            try {
                //Get the input stream
                InputStream is = connection.getInputStream();
                //Send input stream to string (Downloaded as Byte Array)
                byte[] data = IOUtils.toByteArray(is);
                //When it's done, close the connection (Required)
                is.close();
                //Return Stringified Data
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //If there is no network data return an empty byte array
        return new byte[0];
    }

}
