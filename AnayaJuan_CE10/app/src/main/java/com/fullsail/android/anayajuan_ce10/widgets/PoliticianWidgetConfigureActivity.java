// Juan Pablo Anaya
// MDF3 - 201608
// PoliticianWidgetConfigureActivity

package com.fullsail.android.anayajuan_ce10.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.fragments.SettingsFragment;

public class PoliticianWidgetConfigureActivity extends Activity {

    //TAG
    //private static final String TAG = "PoliticianWidgetConfAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent launcherIntent = getIntent();
        Bundle extras = launcherIntent.getExtras();

        int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        //mWidgetId = launcherIntent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        //Dev
        //Log.i(TAG, "onCreate: " + "WIDGET ID: " + mWidgetId);

        if (extras != null) {
            mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            setResult(RESULT_CANCELED); // Removes widget from the home_screen
            finish();
        }

        if (savedInstanceState == null) {
            SettingsFragment frag = SettingsFragment.newInstance(mWidgetId);
            getFragmentManager().beginTransaction().replace(R.id.container, frag, SettingsFragment.TAG).commit();
        }
    }
}