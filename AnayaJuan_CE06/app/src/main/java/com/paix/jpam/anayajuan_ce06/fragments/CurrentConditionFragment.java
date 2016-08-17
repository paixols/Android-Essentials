// Juan Pablo Anaya
// MDF3 - 201608
// CurrentConditionFragment

package com.paix.jpam.anayajuan_ce06.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;
import com.squareup.picasso.Picasso;

public class CurrentConditionFragment extends Fragment {

    //TAG
    private static final String TAG = "CurrentConditionFrag";

    /*Constructor*/
    public CurrentConditionFragment newInstanceOf(Context context, Weather weather) {
        //New Instance
        CurrentConditionFragment currentConditionFrag = new CurrentConditionFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.weather_key), weather);
        currentConditionFrag.setArguments(args);
        //Return Instance
        return currentConditionFrag;
    }

    /*LifeCycle*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Custom View
        View view = inflater.inflate(R.layout.fragment_currentcondition, container, false);
        //Weather Information
        Bundle args = getArguments();
        if (args != null) {
            Weather weather = (Weather) args.getSerializable(getContext().getString(R.string.weather_key));
            if (weather != null) {
            /*UI*/
                //Weather Icon
                ImageView weatherIcon = (ImageView) view.findViewById(R.id.ImageView_CurrentCondition_Icon);
                Picasso.with(getContext()).load(weather.getCurrentConditionInfo().getWeatherIconUrl()).into(weatherIcon);
                TextView tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_Time);
                tv.setText(weather.getLocationInfo().getLocalTime());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_Description);
                tv.setText(weather.getCurrentConditionInfo().getWeatherDescription());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_TempC);
                tv.setText(weather.getCurrentConditionInfo().getTempC());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_TempF);
                tv.setText(weather.getCurrentConditionInfo().getTempF());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_TempFeelsC);
                tv.setText(weather.getCurrentConditionInfo().getFeelsLikeTempC());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_TempFeelsF);
                tv.setText(weather.getCurrentConditionInfo().getFeelsLikeTempF());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_WindMi);
                tv.setText(weather.getCurrentConditionInfo().getWindSpeedMiles());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_WindKm);
                tv.setText(weather.getCurrentConditionInfo().getWindSpeedKm());
                tv = (TextView) view.findViewById(R.id.TextView_CurrentCondition_Humidity);
                String humidity = weather.getCurrentConditionInfo().getHumidity() + "%";
                tv.setText(humidity);
            }
        }
        //Dev
        Log.i(TAG, "onCreateView: " + "CURRENT_CONDITION_FRAG");
        return view;
    }
}
