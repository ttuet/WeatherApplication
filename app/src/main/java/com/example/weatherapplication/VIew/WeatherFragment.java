package com.example.weatherapplication.VIew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.Model.Temp;
import com.example.weatherapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;


public class WeatherFragment extends Fragment  {
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    DailyWeatherAdapter dailyAdapter;
    ProgressBar progressBar;
    List<Temp> dailyList;
    List<Temp> hourlyList;

    TextView current_temp;
    TextView current_description;
    TextView current_min;
    TextView feel_like;
    TextView humidity;
    TextView uv;
    TextView wind_deg;
    TextView wind_speed;
    View view;
    RecyclerView recyclerView;
    RecyclerView dailyForecast;
    CustomGauge customGauge;
    String id;

    SharedPreferences Var;
    SharedPreferences pre;
    SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;


    public WeatherFragment(String id) {
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        addControl();
        mapValue();



        dailyAdapter = new DailyWeatherAdapter(getContext(),dailyList);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),hourlyList);
        pre.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        dailyForecast.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        dailyForecast.setAdapter(dailyAdapter);





        return view;
    }

    private void addControl() {
        customGauge = view.findViewById(R.id.gauge);
        recyclerView = view.findViewById(R.id.forecast);
        dailyForecast = view.findViewById(R.id.daily);
        current_temp = view.findViewById(R.id.current_temp);
        current_description = view.findViewById(R.id.current_description);
        current_min = view.findViewById(R.id.current_temP_min);
        feel_like = view.findViewById(R.id.feel_like);
        humidity = view.findViewById(R.id.humidity);
        uv= view.findViewById(R.id.uv);
        wind_deg = view.findViewById(R.id.wind);
        wind_speed = view.findViewById(R.id.wind_speed);
        progressBar = view.findViewById(R.id.progrss);
        Var = getContext().getSharedPreferences("Vari",Context.MODE_PRIVATE);
        pre = getContext().getSharedPreferences("Weather",Context.MODE_PRIVATE);
        onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                mapValue();
            }
        };
        Var.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    private void mapValue() {
        dailyList = new ArrayList<Temp>();
        hourlyList = new ArrayList<Temp>();
        String doc = Var.getString("doC","°C");
        double t = 0;
        if(doc.equalsIgnoreCase("°F")){
            t = 273;
        }
        String result = pre.getString(id,null);
        if(result!=null) {
            progressBar.setVisibility(View.GONE);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                JSONObject current = jsonObject.getJSONObject("current");
                JSONArray hourly = jsonObject.getJSONArray("hourly");
                JSONArray daily = jsonObject.getJSONArray("daily");
                JSONArray weather = current.getJSONArray("weather");
                String temp = current.getString("temp");
                current_temp.setText(Math.round(Double.parseDouble(temp)+t) + "°");
                String desc = weather.getJSONObject(0).getString("description");
                current_description.setText(desc);
                for (int i = 0; i < hourly.length(); i++) {
                    JSONObject objArr = hourly.getJSONObject(i);
                    String dtH = objArr.getString("dt");
                    String tempH = objArr.getString("temp");
                    tempH = Math.round(Double.parseDouble(tempH)+t)+"";
                    JSONArray weath = objArr.getJSONArray("weather");
                    String icon = weath.getJSONObject(0).getString("icon");
                    Temp newTemp = new Temp(dtH, tempH, icon);
                    hourlyList.add(newTemp);
                }
                for (int i = 0; i < daily.length(); i++) {
                    JSONObject tempDaily = daily.getJSONObject(i);
                    String dtH = tempDaily.getString("dt");
                    JSONObject tempH = tempDaily.getJSONObject("temp");
                    String tempMin = tempH.getString("min");
                    String tempMax = tempH.getString("max");
                    JSONArray weath = tempDaily.getJSONArray("weather");
                    String icon = weath.getJSONObject(0).getString("icon");
                    if (i == 0) {
                        current_min.setText(Math.round(Double.parseDouble(tempMin)+t) + "° / " + Math.round(Double.parseDouble(tempMax)+ t) +"°");
                    } else {
                        String tempMax_Min = Math.round(Double.parseDouble(tempMin)+t) + "° / " + Math.round(Double.parseDouble(tempMax)+ t) +"°";
                        Temp newTemp = new Temp(dtH, tempMax_Min, icon);
                        dailyList.add(newTemp);
                    }
                }
                String humidi = current.getString("humidity");
                humidity.setText(humidi + '%');
                int humi = Integer.parseInt(humidi);
                customGauge.setValue(humi);
                String feel = current.getString("feels_like");
                feel_like.setText(Math.round(Double.parseDouble(feel)+ t) +"°");
                String uvIndex = current.getString("uvi");
                uv.setText(uvIndex);
                String windeg = current.getString("wind_deg");
                wind_deg.setText(windeg + "°");
                String winSpeed = current.getString("wind_speed");
                wind_speed.setText(winSpeed + " m/s");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else{
            progressBar.setVisibility(View.VISIBLE);
        }
    }



    public String getIdc(){
        return id;
    }
    public void setId(String city){
        id = city;
    }

}
