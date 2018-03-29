package com.hamdi.hasan.hi_news.View;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hamdi.hasan.hi_news.Controller.Adapters.news_adapter;
import com.hamdi.hasan.hi_news.Model.DB_Provider.DB_Contract;
import com.hamdi.hasan.hi_news.Model.articles;
import com.hamdi.hasan.hi_news.Model.source;
import com.hamdi.hasan.hi_news.R;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        setTitle("favourite");

        RecyclerView recyclerView = findViewById(R.id.rcv_favourite_news);

        ArrayList<articles> Data = new ArrayList<>();

        Cursor cr = getContentResolver().query(DB_Contract.CONTENT_URI,null, null,null
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
        }else {
            Log.d("HASSAN", "getData: null");
        }

        if (cr != null) {
            cr.close();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FavouriteActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        news_adapter mAdapter = new news_adapter(FavouriteActivity.this, Data);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FavouriteActivity.this, MainActivity.class));
        finish();
    }
}
