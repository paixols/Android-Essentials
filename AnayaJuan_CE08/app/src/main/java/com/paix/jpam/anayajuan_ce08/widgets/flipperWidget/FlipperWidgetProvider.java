// Juan Pablo Anaya
// MDF3 - 201608
// FlipperWidgetProvider

package com.paix.jpam.anayajuan_ce08.widgets.flipperWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.widgets.UpdateHelper;

public class FlipperWidgetProvider extends AppWidgetProvider {

    //TAG
    private static final String TAG = "FlipperWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateFlipperViewWidget(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateFlipperWidget(context);
        //Dev
        Log.i(TAG, "onUpdate: " + "FLIPPER_WIDGET_PROVIDER");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Switch for the actions
        switch (intent.getAction()) {
            case UpdateHelper.NEXT_PHOTO_BROADCAST:
                //Dev
                Log.i(TAG, "onReceive: " + "NEXT_PHOTO");
                RemoteViews rvNext = new RemoteViews(context.getPackageName(), R.layout.flipperwidget);
                rvNext.showNext(R.id.ViewFlipper_Widget);
                AppWidgetManager.getInstance(context).partiallyUpdateAppWidget
                        (intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID), rvNext);
                break;

            case UpdateHelper.PREVIOUS_PHOTO_BROADCAST:
                //Dev
                Log.i(TAG, "onReceive: " + "PREVIOUS_PHOTOS");
                RemoteViews rvPrevious = new RemoteViews(context.getPackageName(), R.layout.flipperwidget);
                rvPrevious.showPrevious(R.id.ViewFlipper_Widget);
                AppWidgetManager.getInstance(context).partiallyUpdateAppWidget
                        (intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID), rvPrevious);
                break;
        }
        super.onReceive(context, intent);
    }
}
