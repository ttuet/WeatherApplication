package com.example.weatherapplication.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LoadWeatherWorker extends Worker{
    ArrayList<City> cityArrayList ;

    public LoadWeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String url ="https://api.openweathermap.org/data/2.5/onecall?lat=21.02&lon=105.84&appid=c3b45d9603f611484b21cba61752dd81&units=metric&lang=vi";
        try {
            String response = getJsonFromServer(url);
//            SharedPreferences pre = getApplicationContext().getSharedPreferences("Weather",'i');
//            pre.edit().putString("Hanoi",response).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    private void getCityList() throws IOException {

    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));
        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }
}
