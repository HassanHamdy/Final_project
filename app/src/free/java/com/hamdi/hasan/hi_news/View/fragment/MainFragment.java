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
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
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
    private AdView mAdView;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.rcv_news);

        MobileAds.initialize(getActivity(), "ca-app-pub-1856757223279588~1272163117");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(getActivity(),"failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
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
                ""
                , getActivity());
    }



}
