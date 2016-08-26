// Juan Pablo Anaya
// MDF3 - 201608
// PoliticianWidget (Provider)

package com.fullsail.android.anayajuan_ce10.widgets;

import com.fullsail.android.anayajuan_ce10.R;
import com.fullsail.android.anayajuan_ce10.VotingHistoryActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class PoliticianWidget extends AppWidgetProvider {

    /*LifeCycle*/
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {

            int widgetId = appWidgetIds[i];

            Intent intent = new Intent(context, ListWidgetService.class);
            intent.setData(Uri.fromParts("content", String.valueOf(widgetId), null)); // Leave this line alone.
            intent.putExtra(ListWidgetService.EXTRA_TYPE, ListWidgetService.TYPE_POLITICIAN);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_widget_layout);
            rv.setRemoteAdapter(R.id.list, intent);//R.id.list
            rv.setEmptyView(R.id.list, R.id.empty);

            Intent historyInfo = new Intent(context, VotingHistoryActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, historyInfo, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list, pIntent);

            appWidgetManager.updateAppWidget(widgetId, rv);
        }
    }

}
