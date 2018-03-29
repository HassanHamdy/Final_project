package com.hamdi.hasan.hi_news.Controller.Services;


import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class myServices extends JobService {

    BackgroundTask backgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new BackgroundTask(){
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                jobFinished(job,false); // true => means reschedule the job again
            }
        };

        backgroundTask.execute();
        return true; // true => which means you will use another thread
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true; // true => which retry job again if fail
    }

    public static class BackgroundTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            return "Hello from BG Task";
        }
    }
}
