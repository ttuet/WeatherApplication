package com.example.weatherapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weatherapplication.Model.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {
    public static String getJsonFromServer(String url) throws IOException {

//        BufferedReader inputStream = null;
//
//        URL jsonUrl = new URL(url);
//        URLConnection dc = jsonUrl.openConnection();
//
//        dc.setConnectTimeout(5000);
//        dc.setReadTimeout(5000);
//
//        inputStream = new BufferedReader(new InputStreamReader(
//                dc.getInputStream()));
//        // read the JSON results into a string
//        String jsonResult = inputStream.readLine();
//        return jsonResult;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response responses = null;

        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = null;
        try {
            jsonData = responses.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;


    }
    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


}
