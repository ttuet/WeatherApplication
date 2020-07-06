package com.example.weatherapplication.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.weatherapplication.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LoadWeatherWorker extends Worker{

    String key = "c3b45d9603f611484b21cba61752dd81";
    public LoadWeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences pre = getApplicationContext().getSharedPreferences("Weather",MODE_PRIVATE);
        SharedPreferences cityPre = getApplicationContext().getSharedPreferences("cityList",MODE_PRIVATE);

        String jsonListCity = cityPre.getString("json","i");
        if(!jsonListCity.equalsIgnoreCase("i")) {
            Gson gson = new Gson();
            Type listCityType = new TypeToken<List<City>>() {
            }.getType();

            List<City> cityList = gson.fromJson(jsonListCity, listCityType);

//        String url ="https://api.openweathermap.org/data/2.5/onecall?lat=21.02&lon=105.84&appid=c3b45d9603f611484b21cba61752dd81&units=metric&lang=vi";

            try {
                for (int i = 0; i < cityList.size(); i++) {


                    String response = Utils.getJsonFromServer(addUrl(cityList.get(i).getLat(), cityList.get(i).getLon(), key));
                    pre.edit().putString(cityList.get(i).getId(), response).apply();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Test", "doWork: " + "Faill");
            }
        }

        return Result.success();
    }




    public String addUrl(String lat, String lon,String key){
        return "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat+"&lon="+lon+"&appid="+key+"&units=metric&lang=vi";

    }
}
