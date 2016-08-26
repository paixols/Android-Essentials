// Juan Pablo Anaya
// MDF3 - 201608
// SettingsFragment (Widget)

package com.fullsail.android.anayajuan_ce10.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.VotingHistoryActivity;
import com.fullsail.android.anayajuan_ce10.widgets.ListWidgetService;

public class SettingsFragment extends Fragment implements OnClickListener {

    //TAG
    public static final String TAG = "SettingsFragment.TAG";
    /*Properties*/
    public static final String ARG_WIDGET_ID = "SettingsFragment.ARG_WIDGET_ID";
    private int mWidgetId;

    /*Constructor*/
    public static SettingsFragment newInstance(int widgetId) {
        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_WIDGET_ID, widgetId);
        frag.setArguments(args);
        return frag;
    }

    /*LifeCycle*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Custom View
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        //UI
        v.findViewById(R.id.all).setOnClickListener(this);
        v.findViewById(R.id.favorites).setOnClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args == null || !args.containsKey(ARG_WIDGET_ID)) {
            throw new IllegalArgumentException("You must supply a widget ID to configure.");
        }

        mWidgetId = args.getInt(ARG_WIDGET_ID);
    }

    @Override
    public void onClick(View v) {

        // TODO: Ensure proper update ID and result.

        //Filter
        int filter;
        //All || Favorites ?
        if (v.getId() == R.id.all) {
            filter = PoliticiansListFragment.FILTER_ALL;
        } else {
            filter = PoliticiansListFragment.FILTER_FAVORITES;
        }

        //Set Filter for Data Pulled in View Factory
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putInt("Filter" + mWidgetId, filter).commit();//apply()


        Intent intent = new Intent(getActivity(), ListWidgetService.class);
        intent.setData(Uri.fromParts("content", String.valueOf(mWidgetId), null)); // Leave this line alone.
        intent.putExtra(ListWidgetService.EXTRA_TYPE, ListWidgetService.TYPE_POLITICIAN);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mWidgetId);

        RemoteViews rv = new RemoteViews(getActivity().getPackageName(), R.layout.list_widget_layout);
        rv.setRemoteAdapter(R.id.list, intent);//empty
        rv.setEmptyView(R.id.list, R.id.empty);

        Intent historyInfo = new Intent(getActivity(), VotingHistoryActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 0, historyInfo, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.list, pIntent);

        AppWidgetManager mgr = AppWidgetManager.getInstance(getActivity());
        mgr.updateAppWidget(mWidgetId, rv);//-1

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
        getActivity().setResult(Activity.RESULT_OK, result); //RESULT_CANCELLED
        getActivity().finish();
    }
}