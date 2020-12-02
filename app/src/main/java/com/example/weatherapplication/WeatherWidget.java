package com.example.weatherapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.weatherapplication.VIew.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {
    String temp, tempMin_, icon;
    private AppWidgetTarget appWidgetTarget;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        SharedPreferences pre = context.getSharedPreferences("Weather",Context.MODE_PRIVATE);
        String result = pre.getString("1",null);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject current = jsonObject.getJSONObject("current");
            temp = current.getString("temp");
            JSONArray daily = jsonObject.getJSONArray("daily");
            JSONObject tempDaily = daily.getJSONObject(0);
            JSONObject tempH = tempDaily.getJSONObject("temp");
            String tempMin = tempH.getString("min");
            String tempMax = tempH.getString("max");
            tempMin_=Math.round(Double.parseDouble(tempMin)) + "° / " + Math.round(Double.parseDouble(tempMax)) + "°";
            JSONArray weath = tempDaily.getJSONArray("weather");
            icon = weath.getJSONObject(0).getString("icon");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        rv.setTextViewText(R.id.widget_temp,Math.round(Double.parseDouble(temp)) + "°");
        rv.setTextViewText(R.id.widget_temp_min,tempMin_);
        appWidgetTarget = new AppWidgetTarget(context, R.id.widget_icon, rv, appWidgetIds) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
            }
        };
        String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";

        Glide.with( context.getApplicationContext() )
                .asBitmap()
                .load(iconUrl)
                .into( appWidgetTarget );

        pushWidgetUpdate(context, rv);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews rv) {
        ComponentName myWidget = new ComponentName(context, WeatherWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, rv);
    }
}

