// Juan Pablo Anaya
// MDF3 - 201608
// ListWidgetService

package com.fullsail.android.anayajuan_ce10.widgets;

import com.fullsail.android.anayajuan_ce10.widgets.factory.PoliticianViewFactory;
import com.fullsail.android.anayajuan_ce10.widgets.factory.VoteHistoryViewFactory;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {

    //TAG
    private static final String TAG = "ListWidgetService";

    /*Properties*/
    public static final String EXTRA_TYPE = "ListWidgetService.EXTRA_TYPE";

    /*Widget Type*/
    public static final int TYPE_POLITICIAN = 0x03001;
    public static final int TYPE_VOTE_HISTORY = 0x03002;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        //Needs Extra Type
        if (!intent.hasExtra(EXTRA_TYPE)) {
            throw new IllegalArgumentException("You must provide a widget type.");
        }
        //Get Extra (WIDGET TYPE)
        int type = intent.getIntExtra(EXTRA_TYPE, -1);

        //if Type Invalid
        if (type == -1) {
            throw new IllegalArgumentException("You must provide a valid widget type.");
        }
        //Get Widget ID
        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        //DEV
        Log.i(TAG, "onGetViewFactory: " + "LIST WIDGET SERVICE: WIDGET ID: " + widgetId);

        //Invalid Widget ID Check
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            throw new IllegalArgumentException("You must provide a valid widget ID.");
        }

        //Try switch statement
        if (type == TYPE_POLITICIAN) {
            return new PoliticianViewFactory(getApplicationContext(), widgetId);
        } else if (type == TYPE_VOTE_HISTORY) {
            return new VoteHistoryViewFactory(getApplicationContext(), widgetId);
        }

        return null;
    }
}
