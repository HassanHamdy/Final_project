package com.hamdi.hasan.hi_news.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hamdi.hasan.hi_news.Model.DB_Provider.DB_Contract;
import com.hamdi.hasan.hi_news.Model.articles;
import com.hamdi.hasan.hi_news.Model.source;
import com.hamdi.hasan.hi_news.R;
import com.hamdi.hasan.hi_news.View.FavouriteActivity;

import java.util.ArrayList;

/**
 * Created by Owner on 3/28/2018.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int appWidgetId;
    private ArrayList<articles> Data;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        Data = getData();
    }

    public ArrayList<articles> getData(){
        Data = new ArrayList<>();
        Cursor cr = context.getContentResolver().query(DB_Contract.CONTENT_URI,null, null,null
                , null);

        if (cr != null && cr.moveToFirst()){
            do{
                source source = new source("-1", cr.getString(cr.getColumnIndex(DB_Contract.COLUMN_NEWS_SOURCE)));

                Data.add(new articles(source,
                        " ",cr.getString(cr.getColumnIndex(DB_Contract.COLUMN_NEWS_TITLE))
                        , cr.getString(cr.getColumnIndex(DB_Contract.COLUMN_NEWS_DESCRIPTION)), "",
                        cr.getString(cr.getColumnIndex(DB_Contract.COLUMN_NEWS_IMAGE)),
                        cr.getString(cr.getColumnIndex(DB_Contract.COLUMN_NEWS_PUBLISH_AT)), 1));
            } while (cr.moveToNext());
        }

        if (cr != null) {
            cr.close();
        }
        return Data;
    }
        @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_list_item);
        remoteView.setTextViewText(R.id.widget_item_title, Data.get(i).getTitle());
        remoteView.setTextViewText(R.id.widget_item_description, Data.get(i).getDescription());
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
