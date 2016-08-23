// Juan Pablo Anaya
// MDF3 - 201608
// UpdateHelper

package com.paix.jpam.anayajuan_ce08.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.paix.jpam.anayajuan_ce08.R;
import com.paix.jpam.anayajuan_ce08.widgets.flipperWidget.FlipperWidgetProvider;
import com.paix.jpam.anayajuan_ce08.widgets.flipperWidget.FlipperWidgetService;
import com.paix.jpam.anayajuan_ce08.widgets.stackWidget.StackWidgetProvider;
import com.paix.jpam.anayajuan_ce08.widgets.stackWidget.StackWidgetService;

public class UpdateHelper {

    //TAG
    //private static final String TAG = "UpdateHelper";

    /*Update Widget Helper for STACK_VIEW_WIDGET*/
    public static void updateStackViewWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            //Remote Views
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stackwidget);

            //Intent for Widget Service (It will create the views)
            Intent stackViewsServiceIntent = new Intent(context, StackWidgetService.class);
            stackViewsServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            stackViewsServiceIntent.setData(Uri.parse(stackViewsServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));//Embeds Extras
            rv.setRemoteAdapter(R.id.StackView_Widget, stackViewsServiceIntent);
            rv.setEmptyView(R.id.StackView_Widget, R.id.TextView_StackView_Widget_EmptyView);

            //Intent to start default image viewer
            Intent imageViewer = new Intent(Intent.ACTION_VIEW);
            imageViewer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent imageViewerPendingIntent = PendingIntent.getActivity(context, 0, imageViewer, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.StackView_Widget, imageViewerPendingIntent);


            //App Widget Manager
            appWidgetManager.updateAppWidget(widgetId, rv);
        }
    }

    /*Update Widget Helper for FLIPPER_VIEW_WIDGET*/
    public static void updateFlipperViewWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            //Remote Views
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.flipperwidget);

            //Intent for Widget Service (It will create the Views)
            Intent flipperViewServiceIntent = new Intent(context, FlipperWidgetService.class);
            flipperViewServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            flipperViewServiceIntent.setData(Uri.parse(flipperViewServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));//Embeds Extras
            rv.setRemoteAdapter(R.id.ViewFlipper_Widget, flipperViewServiceIntent);
            rv.setEmptyView(R.id.ViewFlipper_Widget, R.id.TextView_Flipper_Widget_EmptyView);

            //Intent to start default image viewer
            Intent imageViewer = new Intent(Intent.ACTION_VIEW);
            imageViewer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent imageViewerPendingIntent = PendingIntent.getActivity(context, 0, imageViewer, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.ViewFlipper_Widget, imageViewerPendingIntent);

            //App Widget Manager
            appWidgetManager.updateAppWidget(widgetId, rv);
        }
    }

    /*Notify Data Changed for Widgets*/
    public static void updateStackWidget(Context context) {
        //Notify that there is a new Data set
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, StackWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsIds, R.id.StackView_Widget);
    }

    public static void updateFlipperWidget(Context context) {
        //Notify that there is a new Data set
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, FlipperWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsIds, R.id.ViewFlipper_Widget);
    }
}
