package com.fullsail.android.politicalwidgets;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.fullsail.android.politicalwidgets.widgets.PoliticianWidget;

public class WidgetUpdater extends BroadcastReceiver {
	
	public static final String ACTION_UPDATE_WIDGETS = "com.fullsail.android.politicalwidgets.ACTION_UPDATE_WIDGETS";

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(ACTION_UPDATE_WIDGETS)) {
			AppWidgetManager mgr = AppWidgetManager.getInstance(context);
			int[] ids = mgr.getAppWidgetIds(new ComponentName(context, PoliticianWidget.class));
			// TODO: Tell politician widgets to update their data.
		}
	}
}
