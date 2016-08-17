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

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;

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

            }
        }
        return view;
    }
}
