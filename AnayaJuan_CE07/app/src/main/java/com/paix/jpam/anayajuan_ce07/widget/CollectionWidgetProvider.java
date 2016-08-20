// Juan Pablo Anaya
// MDF3 - 201608
// CollectionWidgetProvider

package com.paix.jpam.anayajuan_ce07.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class CollectionWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        /*Widget Update Helper*/
        UpdateHelper.updateWidget(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateWidgetListView(context);
    }
}
