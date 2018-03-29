package com.hamdi.hasan.hi_news.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.hamdi.hasan.hi_news.R;
import com.hamdi.hasan.hi_news.View.FavouriteActivity;
import com.hamdi.hasan.hi_news.View.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class NewsAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        Intent intent = new Intent(context, FavouriteActivity.class);

        // Creating a pending intent, which will be invoked when the user
        // clicks on the widget
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent,0);

        RemoteViews remoteViews = updateWidgetListView(context,
                appWidgetId);
        remoteViews.setOnClickPendingIntent(R.id.widget,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews updateWidgetListView(Context context,
                                                    int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.news_app_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.news_listView,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.news_listView, R.id.empty_view);
        return remoteViews;
    }
}

