package com.example.weatherapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.DailyWeatherAdapter;
import com.example.weatherapplication.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;


public class WeatherFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    DailyWeatherAdapter dailyAdapter;

    ArrayList<String> timee;
    ArrayList<String> timeForecast;
    ArrayList<String> tempForecast;
    ArrayList<String> iconForecast;

    TextView current_temp;
    TextView current_description;
    TextView current_min;
    TextView feel_like;
    TextView humidity;
    TextView uv;
    TextView wind;
    TextView wind_speed;
    View view;
    RecyclerView recyclerView;
    RecyclerView dailyForecast;
    CustomGauge customGauge;
    String title;


    public WeatherFragment(String title) {
            this.title = title;
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

        dailyAdapter = new DailyWeatherAdapter(getContext(),timee,tempForecast,iconForecast);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),timeForecast,tempForecast,iconForecast);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        dailyForecast.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(layoutManager);
        myRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        dailyForecast.setAdapter(dailyAdapter);

        customGauge.setValue(90);




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
        wind = view.findViewById(R.id.wind);
        wind_speed = view.findViewById(R.id.wind_speed);
    }

    private void mapValue() {
        timeForecast = new ArrayList<>();
        tempForecast = new ArrayList<>();
        iconForecast = new ArrayList<>();
        timee  = new ArrayList<>();

        timeForecast.add("16");
        timeForecast.add("16");
        timeForecast.add("16");
        timeForecast.add("16");
        timeForecast.add("16");
        tempForecast.add("35>");
        tempForecast.add("35>");
        tempForecast.add("35>");tempForecast.add("35>");
        tempForecast.add("35>");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");iconForecast.add("10d");
        timee.add("13:00");
        timee.add("13:00");timee.add("13:00");timee.add("13:00");timee.add("13:00");




















    }

    public void onItemClick(View view,int position) {
        Toast.makeText(view.getContext(), "You clicked " + myRecyclerViewAdapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();

    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String city){
        title = city;
    }

}
