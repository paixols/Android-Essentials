// Juan Pablo Anaya
// MDF3 - 201608
// ForecastFragment

package com.paix.jpam.anayajuan_ce06.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.squareup.picasso.Picasso;

public class ForecastFragment extends Fragment {

    //TAG
    private static final String TAG = "ForecastFragment";

    /*Constructor*/
    public ForecastFragment newInstanceOf(Context context, Weather weather) {
        //Create Instance
        ForecastFragment forecastFragment = new ForecastFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.weather_key), weather);
        forecastFragment.setArguments(args);
        //Return Instance
        return forecastFragment;
    }

    /*LifeCycle*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Custom View
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        //Bundle
        Bundle args = getArguments();
        if (args != null) {
            Weather weather = (Weather) args.getSerializable(getContext().getString(R.string.weather_key));
            if (weather != null) {
                /*UI*/
                //Day One
                ImageView iv = (ImageView) view.findViewById(R.id.ImageView_ForecastOne_Icon);
                Picasso.with(getContext()).load(weather.getThreeDayWeatherForecast().getFirstDay()
                        .getWeatherIconUrl()).into(iv);
                TextView tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_Time);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getDate());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_Description);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getWeatherDescription());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_TempC);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getMaxTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_TempF);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getMaxTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_TempFeelsC);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getMinTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_TempFeelsF);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getMinTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastOne_Humidity);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getHumidity());


                //Day Two
                iv = (ImageView) view.findViewById(R.id.ImageView_ForecastTwo_Icon);
                Picasso.with(getContext()).load(weather.getThreeDayWeatherForecast().getSecondDay()
                        .getWeatherIconUrl()).into(iv);
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_Time);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getDate());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_Description);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getWeatherDescription());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_TempC);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getMaxTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_TempF);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getMaxTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_TempFeelsC);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getMinTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_TempFeelsF);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getMinTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastTwo_Humidity);
                tv.setText(weather.getThreeDayWeatherForecast().getSecondDay().getHumidity());


                //Day Three
                iv = (ImageView) view.findViewById(R.id.ImageView_ForecastThree_Icon);
                Picasso.with(getContext()).load(weather.getThreeDayWeatherForecast().getThirdDay()
                        .getWeatherIconUrl()).into(iv);
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_Time);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getDate());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_Description);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getWeatherDescription());

                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_TempC);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getMaxTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_TempF);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getMaxTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_TempFeelsC);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getMinTempC());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_TempFeelsF);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getMinTempF());
                tv = (TextView) view.findViewById(R.id.TextView_ForecastThree_Humidity);
                tv.setText(weather.getThreeDayWeatherForecast().getThirdDay().getHumidity());

            }
        }
        return view;
    }
}
