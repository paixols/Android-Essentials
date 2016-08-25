package com.fullsail.android.politicalwidgets.widgets;

import com.fullsail.android.politicalwidgets.widgets.factory.PoliticianViewFactory;
import com.fullsail.android.politicalwidgets.widgets.factory.VoteHistoryViewFactory;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {
	
	public static final String EXTRA_TYPE = "ListWidgetService.EXTRA_TYPE";
	
	public static final int TYPE_POLITICIAN = 0x03001;
	public static final int TYPE_VOTE_HISTORY = 0x03002;

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		if(!intent.hasExtra(EXTRA_TYPE)) {
			throw new IllegalArgumentException("You must provide a widget type.");
		}
		
		int type = intent.getIntExtra(EXTRA_TYPE, -1);
		if(type == -1) {
			throw new IllegalArgumentException("You must provide a valid widget type.");
		}
		
		int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		if(widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			throw new IllegalArgumentException("You must provide a valid widget ID.");
		}
		
		if(type == TYPE_VOTE_HISTORY) {
			return new PoliticianViewFactory(getApplicationContext(), widgetId);
		} else if(type == TYPE_POLITICIAN) {
			return new VoteHistoryViewFactory(getApplicationContext(), widgetId);
		}
		
		return null;
	}
}
