package com.example.weatherapplication.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.weatherapplication.Model.LoadWeatherWorker;

import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class WorkMangerController {
    Context mCtx;
    PeriodicWorkRequest myWork;
    public WorkMangerController(Context context){
        mCtx = context;
    }
    public void setWork(){


            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
            PeriodicWorkRequest.Builder myWorkBuilder = new PeriodicWorkRequest.Builder(LoadWeatherWorker.class, 1, TimeUnit.HOURS)
                    .setConstraints(constraints);
            myWork = myWorkBuilder.build();
            WorkManager.getInstance(mCtx).enqueue(myWork);

    }
    public void cancelWork(){
        WorkManager.getInstance(mCtx).cancelWorkById(myWork.getId());
    }

}
