// Juan Pablo Anaya
// MDF3 - 201608
// AstronomyFragment

package com.paix.jpam.anayajuan_ce06.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;

public class AstronomyFragment extends Fragment {

    //TAG
    private static final String TAG = "AstronomyFragment";

    /*Constructor*/
    public AstronomyFragment newInstanceOf(Context context, Weather weather) {
        //Create Instance
        AstronomyFragment astronomyFrag = new AstronomyFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.weather_key), weather);
        astronomyFrag.setArguments(args);
        //Return Instance
        return astronomyFrag;
    }

    /*LifeCycle*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Custom View
        View view = inflater.inflate(R.layout.fragment_astronomy, container, false);
        //Bundle
        Bundle args = getArguments();
        if (args != null) {
            Weather weather = (Weather) args.getSerializable(getContext().getString(R.string.weather_key));
            if (weather != null) {
                TextView tv = (TextView) view.findViewById(R.id.TextView_Astronomy_DateSun);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getDate());
                tv = (TextView) view.findViewById(R.id.TextView_Astronomy_TimeSun);
                String sunriseSunset = weather.getThreeDayWeatherForecast().getFirstDay().
                        getAstronomyInfo().getSunrise() + " / " + weather.getThreeDayWeatherForecast().
                        getFirstDay().getAstronomyInfo().getSunset();
                tv.setText(sunriseSunset);
                tv = (TextView) view.findViewById(R.id.TextView_Astronomy_DateMoon);
                tv.setText(weather.getThreeDayWeatherForecast().getFirstDay().getDate());
                tv = (TextView) view.findViewById(R.id.TextView_Astronomy_TimeMoon);
                String moonriseMoonset = weather.getThreeDayWeatherForecast().getFirstDay().
                        getAstronomyInfo().getMoonrise() + " / " + weather.getThreeDayWeatherForecast().
                        getFirstDay().getAstronomyInfo().getMoonset();
                tv.setText(moonriseMoonset);
            }
        }
        //Dev
        Log.i(TAG, "onCreateView: " + "ASTRONOMY_FRAG");
        return view;
    }
}
