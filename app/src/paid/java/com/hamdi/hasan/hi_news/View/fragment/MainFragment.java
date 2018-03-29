package com.hamdi.hasan.hi_news.View.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.google.gson.Gson;
import com.hamdi.hasan.hi_news.Controller.Adapters.news_adapter;
import com.hamdi.hasan.hi_news.Controller.Network.BaseRequest;
import com.hamdi.hasan.hi_news.Controller.Network.RequestCallback;
import com.hamdi.hasan.hi_news.Controller.Services.myServices;
import com.hamdi.hasan.hi_news.Model.newsData;
import com.hamdi.hasan.hi_news.R;
import com.hamdi.hasan.hi_news.View.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    public static final String myPreference = "myPref";
    public static final String isFirstTime = "Check";
    private static final String Job_tag = "my_job_tag";
    private FirebaseJobDispatcher firebaseJobDispatcher;



    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.rcv_news);


//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myPreference,Context.MODE_PRIVATE);
//
//        if(sharedPreferences.getBoolean(isFirstTime,true)){
//
//            SharedPreferences.Editor editor = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE).edit();
//            editor.putBoolean(isFirstTime, false);
//            editor.apply();
//        }

        getDataFromApi();


//        firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getActivity()));
//        Job job = firebaseJobDispatcher.newJobBuilder()
//                .setService(myServices.class)
//                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
//                .setRecurring(true)
//                .setTag(Job_tag)
//                .setTrigger(Trigger.executionWindow(10,15))
//                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                .setReplaceCurrent(false)
//                .setConstraints(Constraint.ON_ANY_NETWORK).build();
//        firebaseJobDispatcher.mustSchedule(job);


        return view;
    }

    public void getDataFromApi(){
        BaseRequest.DoGet(new RequestCallback() {
                              @Override
                              public void Success(String response) {
                                  Gson gson = new Gson();
                                  newsData Data = gson.fromJson(response, newsData.class);

                                  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                  recyclerView.setLayoutManager(mLayoutManager);
                                  recyclerView.setItemAnimator(new DefaultItemAnimator());
                                  news_adapter mAdapter = new news_adapter(getActivity(),Data.getArticles());
                                  recyclerView.setAdapter(mAdapter);
                              }

                              @Override
                              public void Error(Exception ex) {

                              }
                          },
                "https://newsapi.org/v2/top-headlines?sources=google-news,fox-news&apiKey=c082b22dcd57462e91b7e7dbcfacd697"
                , getActivity());
    }



}
