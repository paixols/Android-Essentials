// Juan Pablo Anaya
// MDF3 - 201608
// StackWidgetProvider

package com.paix.jpam.anayajuan_ce08.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class StackWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateWidget(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateStackWidget(context);
    }
}
