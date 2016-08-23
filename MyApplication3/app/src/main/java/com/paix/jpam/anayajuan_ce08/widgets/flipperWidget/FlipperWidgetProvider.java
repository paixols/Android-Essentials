// Juan Pablo Anaya
// MDF3 - 201608
// FlipperWidgetProvider

package com.paix.jpam.anayajuan_ce08.widgets.flipperWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.paix.jpam.anayajuan_ce08.widgets.UpdateHelper;

public class FlipperWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateFlipperViewWidget(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateFlipperWidget(context);
    }
}
