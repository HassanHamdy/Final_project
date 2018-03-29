package com.hamdi.hasan.hi_news.View.fragment;


import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hamdi.hasan.hi_news.Model.DB_Provider.DB_Contract;
import com.hamdi.hasan.hi_news.Model.articles;
import com.hamdi.hasan.hi_news.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private ImageView newsImage;
    private TextView newsTitle, newsPublishAt, newsSource, newsDescription;
    private ImageButton favBTN;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        final articles Data = getArguments().getParcelable("newsDetails");

        newsImage = view.findViewById(R.id.img_newsDetail);
        newsTitle = view.findViewById(R.id.txt_newsDetail_title);
        newsPublishAt = view.findViewById(R.id.txt_newsDetail_publishAt);
        newsSource = view.findViewById(R.id.txt_newsDetail_source);
        newsDescription = view.findViewById(R.id.txt_newsDetail_description);
        favBTN = view.findViewById(R.id.btn_favourite);

        if(Data.getIsFav() == 0){
            favBTN.setImageResource(android.R.drawable.btn_star_big_off);
            favBTN.setTag("off");
        }else {
            favBTN.setImageResource(android.R.drawable.btn_star_big_on);
            favBTN.setTag("on");
        }

        favBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favBTN.getTag() == "off"){
                    favBTN.setImageResource(android.R.drawable.btn_star_big_on);
                    favBTN.setTag("on");

                    Data.setIsFav(1);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DB_Contract.COLUMN_NEWS_IMAGE, Data.getUrlToImage());
                    contentValues.put(DB_Contract.COLUMN_NEWS_TITLE, Data.getTitle());
                    contentValues.put(DB_Contract.COLUMN_NEWS_PUBLISH_AT, Data.getPublishedAt());
                    contentValues.put(DB_Contract.COLUMN_NEWS_SOURCE, Data.getSource().getName());
                    contentValues.put(DB_Contract.COLUMN_NEWS_DESCRIPTION, Data.getDescription());

                    // Insert the content values via a ContentResolver
                    Uri uri = getActivity().getContentResolver().insert(DB_Contract.CONTENT_URI, contentValues);

                    if(uri != null) {
                        Snackbar.make(newsDescription,"news added to favourite", Snackbar.LENGTH_SHORT).show();
                    }

                }else {
                    favBTN.setImageResource(android.R.drawable.btn_star_big_off);
                    favBTN.setTag("off");
                    Data.setIsFav(0);

                    int mRowsDeleted = getActivity().getContentResolver().delete(DB_Contract.CONTENT_URI, DB_Contract.COLUMN_NEWS_TITLE + "=?", new String[]{Data.getTitle()});

                    if(mRowsDeleted == 1){
                        Snackbar.make(newsDescription,"news removed from favourite", Snackbar.LENGTH_SHORT).show();
                    }
                }


            }
        });

        Picasso.with(getActivity()).load(Data.getUrlToImage())
                .into(newsImage);
        newsTitle.setText(Data.getTitle());
        newsPublishAt.setText(Data.getPublishedAt());
        Log.d("HASSAN", "onCreateView: " + Data.getSource());
        newsSource.setText(Data.getSource().getName());
        newsDescription.setText(Data.getDescription());
        return view;
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }

}
