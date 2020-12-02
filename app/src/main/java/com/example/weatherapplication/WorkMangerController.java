package com.example.weatherapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.weatherapplication.Model.LoadWeatherWorker;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

import static android.content.Context.MODE_PRIVATE;

public class WorkMangerController {
    Context mCtx;
    PeriodicWorkRequest myWork;
    public WorkMangerController(Context context){
        mCtx = context;
    }
    public void setWork(int time){


            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest.Builder myWorkBuilder = new PeriodicWorkRequest.Builder(LoadWeatherWorker.class, time, TimeUnit.HOURS)
                .setConstraints(constraints);
        myWork = myWorkBuilder.build();

        WorkManager.getInstance(mCtx).
        enqueue(myWork);


    }
    public void cancelWork(){
        WorkManager.getInstance(mCtx).cancelAllWork();
    }

}
