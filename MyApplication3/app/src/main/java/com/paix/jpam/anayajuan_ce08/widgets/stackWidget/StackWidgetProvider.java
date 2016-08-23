// Juan Pablo Anaya
// MDF3 - 201608
// StackWidgetProvider

package com.paix.jpam.anayajuan_ce08.widgets.stackWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.paix.jpam.anayajuan_ce08.widgets.UpdateHelper;

public class StackWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateStackViewWidget(context, appWidgetManager, appWidgetIds);
        UpdateHelper.updateStackWidget(context);
    }
}
