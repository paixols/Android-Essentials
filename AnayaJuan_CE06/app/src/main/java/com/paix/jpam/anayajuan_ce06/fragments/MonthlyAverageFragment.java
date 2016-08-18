// Juan Pablo Anaya
// MDF3 - 201608
// MonthlyAverageFragment

package com.paix.jpam.anayajuan_ce06.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paix.jpam.anayajuan_ce06.R;
import com.paix.jpam.anayajuan_ce06.dataModel.Weather;

public class MonthlyAverageFragment extends Fragment {

    //TAG
    private static final String TAG = "MonthlyAverageFragment";

    /*Constructor*/
    public MonthlyAverageFragment newInstanceOf(Context context, Weather weather) {
        //Create Instance
        MonthlyAverageFragment monthlyAverageFrag = new MonthlyAverageFragment();
        //Bundle
        Bundle args = new Bundle();
        args.putSerializable(context.getString(R.string.weather_key), weather);
        monthlyAverageFrag.setArguments(args);
        //Return Instance
        return monthlyAverageFrag;
    }

    /*LifeCycle*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Custom View
        View view = inflater.inflate(R.layout.fragment_monthaverage, container, false);
        Bundle args = getArguments();
        if (args != null) {
            Weather weather = (Weather) args.getSerializable(getContext().getString(R.string.weather_key));
            if (weather != null) {
                TextView tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Jan);
                tv.setText(weather.getClimateAverageYear().getJanuary().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Feb);
                tv.setText(weather.getClimateAverageYear().getFebruary().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Mar);
                tv.setText(weather.getClimateAverageYear().getMarch().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_April);
                tv.setText(weather.getClimateAverageYear().getApril().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_May);
                tv.setText(weather.getClimateAverageYear().getMay().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Jun);
                tv.setText(weather.getClimateAverageYear().getJune().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Jul);
                tv.setText(weather.getClimateAverageYear().getJuly().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Aug);
                tv.setText(weather.getClimateAverageYear().getAugust().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Sep);
                tv.setText(weather.getClimateAverageYear().getSeptember().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Oct);
                tv.setText(weather.getClimateAverageYear().getOctober().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Nov);
                tv.setText(weather.getClimateAverageYear().getNovember().getAbsMaxBoth());
                tv = (TextView) view.findViewById(R.id.TextView_MonthAverage_Dec);
                tv.setText(weather.getClimateAverageYear().getDecember().getAbsMaxBoth());
            }
        }
        return view;
    }
}
