package com.paix.jpam.anayajuan_ce07.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by JPAM on 8/20/16.
 */
public class CollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        /*Use application context to avoid memory leaks*/
        return new CollectionWidgetViewFactory(getApplicationContext());
    }
}
