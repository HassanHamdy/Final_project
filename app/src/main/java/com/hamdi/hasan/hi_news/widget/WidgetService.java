package com.hamdi.hasan.hi_news.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

// this class defines the Adapter for the ListView

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new ListProvider(getApplicationContext(), intent));
    }
}
