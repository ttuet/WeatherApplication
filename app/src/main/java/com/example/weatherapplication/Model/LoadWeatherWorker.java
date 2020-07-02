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
    ArrayList<City> cityArrayList ;
    String key = "c072ec8e783580454010f40ede063200";
    public LoadWeatherWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences pre = getApplicationContext().getSharedPreferences("Weather",MODE_PRIVATE);
        SharedPreferences cityPre = getApplicationContext().getSharedPreferences("cityList",MODE_PRIVATE);

        String jsonListCity = cityPre.getString("json","i");
        Gson gson = new Gson();
        Type listCityType = new TypeToken<List<City>>(){}.getType();
        Log.i("test", "doWork: "+jsonListCity);
        List<City> cityList = gson.fromJson(jsonListCity,listCityType);
        pre.edit().putInt("number",cityList.size());
//        String url ="https://api.openweathermap.org/data/2.5/onecall?lat=21.02&lon=105.84&appid=c3b45d9603f611484b21cba61752dd81&units=metric&lang=vi";

        try {
            for(int i=0;i<cityList.size();i++){


                String response = getJsonFromServer(addUrl(cityList.get(i).getLat(),cityList.get(i).getLon(),key));
                pre.edit().putString(cityList.get(i).getId(),response).commit();
            }

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
    public String addUrl(String lat, String lon,String key){
        return "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat+"&lon="+lon+"&appid="+key+"&units=metric&lang=vi";

    }
}
