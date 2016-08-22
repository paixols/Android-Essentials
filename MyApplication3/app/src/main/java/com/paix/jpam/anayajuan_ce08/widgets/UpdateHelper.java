// Juan Pablo Anaya
// MDF3 - 201608
// UpdateHelper

package com.paix.jpam.anayajuan_ce08.widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.paix.jpam.anayajuan_ce08.R;

public class UpdateHelper {

    //TAG
    //private static final String TAG = "UpdateHelper";

    /*Update Widget Helper*/
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            //Remote Views
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stackwidget);

            //Intent for Widget Service (It will create the views)
            Intent stackViewsServiceIntent = new Intent(context, StackWidgetService.class);
            stackViewsServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            stackViewsServiceIntent.setData(Uri.parse(stackViewsServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));//Embeds Extras
            rv.setRemoteAdapter(R.id.StackView_Widget, stackViewsServiceIntent);
            rv.setEmptyView(R.id.StackView_Widget, R.id.TextView_StackView_Widget_EmptyView);

            //App Widget Manager
            appWidgetManager.updateAppWidget(widgetId, rv);
        }
    }


//        for (int widgetId : appWidgetIds) {
//            //Widget ID
//            //Remote Views
//            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//
//            /*Create explicit intent to attach our service to the Widget Provider, this is like
//            the adapter for the ListView. This service will power our list */
//            Intent serviceIntent = new Intent(context, CollectionWidgetService.class);
//            widgetView.setRemoteAdapter(R.id.ListView_Widget_Data, serviceIntent);
//
//            //Set UI
//            widgetView.setEmptyView(R.id.ListView_Widget_Data, R.id.TextView_Widget_Empty);
//
//            /*Set Up Pending intent for the Detail Activity to launch on Widget List View item
//            touch event. This is only the action, this will not have data , that will be the task
//            of the fill-in intent on the Collection Widget Factory. THIS ONLY DEFINES THE ACTION !.
//             The fill-in intent goes in the View Factory*/
//            Intent detailActivityIntent = new Intent(context, DetailActivity.class);
//            PendingIntent detailActivityPendingIntent = PendingIntent.getActivity(context, 0, detailActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            //Set Pending intent template
//            widgetView.setPendingIntentTemplate(R.id.ListView_Widget_Data, detailActivityPendingIntent);
//
//            /*Pending intent for the "New" Button on the Widget -> Should open the Form Activity*/
//            Intent formActivityIntent = new Intent(context, FormActivity.class);
//            formActivityIntent.putExtra(context.getString(R.string.Started_From_Widget), true); // Activity started from widget
//            formActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent formActivityPendingIntent = PendingIntent.getActivity(context, 0, formActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            widgetView.setOnClickPendingIntent(R.id.Button_Widget_ToForm, formActivityPendingIntent);
//
//            //App Widget Manager
//            appWidgetManager.updateAppWidget(widgetId, widgetView);


    public static void updateStackWidget(Context context) {
        //Update Fragment with new Saved Data
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, StackWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsIds, R.id.StackView_Widget);
    }

}
