// Juan Pablo Anaya
// MDF3 - 201608
// LocationFragment

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

public class LocationFragment extends Fragment {

    //TAG
    private static final String TAG = "LocationFragment";

    /*Constructor*/
    public LocationFragment newInstanceOf(Context context, Weather weather) {
        //New Instance
        LocationFragment locationFrag = new LocationFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.weather_key), weather);
        locationFrag.setArguments(args);
        //Return Instance
        return locationFrag;
    }

    /*LifeCycle*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Custom layout
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        //Arguments
        Bundle args = getArguments();
        if (args != null) {
            Weather weather = (Weather) args.getSerializable(getString(R.string.weather_key));
            if (weather != null) {
                //UI
                TextView tv = (TextView) view.findViewById(R.id.TextView_Location_City);
                tv.setText(weather.getLocationInfo().getCityName());
                tv = (TextView) view.findViewById(R.id.TextView_Location_UtcOffset);
                String utcOffset = "UTC Offset: " + weather.getLocationInfo().getUtcOffset();
                tv.setText(utcOffset);
            }
        }
        //Dev
        Log.i(TAG, "onCreateView: " + "LOCATION_FRAG");
        return view;
    }

}
