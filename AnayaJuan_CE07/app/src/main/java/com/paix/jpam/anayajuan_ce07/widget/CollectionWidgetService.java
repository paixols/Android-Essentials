// Juan Pablo Anaya
// MDF3 - 201608
// CollectionWidgetService

package com.paix.jpam.anayajuan_ce07.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class CollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        /*Use application context to avoid memory leaks*/
        return new CollectionWidgetViewFactory(getApplicationContext());
    }
}
