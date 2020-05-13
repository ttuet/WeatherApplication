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
import android.widget.Toast;

import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.DailyWeatherAdapter;
import com.example.weatherapplication.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class WeatherFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    DailyWeatherAdapter dailyAdapter;
    List<String> timeForecast;
    List<String> tempForecast;
    List<String> iconForecast;
    RecyclerView recyclerView;
    RecyclerView dailyForecast;
    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        addExampleValue();
        dailyForecast = view.findViewById(R.id.daily);
        dailyAdapter = new DailyWeatherAdapter(getContext(),timeForecast,tempForecast,iconForecast);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),timeForecast,tempForecast,iconForecast);
        recyclerView = view.findViewById(R.id.forecast);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        dailyForecast.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(layoutManager);
        myRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        dailyForecast.setAdapter(dailyAdapter);




        return view;
    }

    private void addExampleValue() {
        timeForecast = new ArrayList<>();
        tempForecast = new ArrayList<>();
        iconForecast = new ArrayList<>();

        timeForecast.add("11");
        timeForecast.add("12");
        timeForecast.add("13");
        timeForecast.add("14");
        timeForecast.add("15");
        timeForecast.add("16");
        timeForecast.add("17");
        timeForecast.add("11");
        timeForecast.add("12");
        timeForecast.add("13");
        timeForecast.add("14");
        timeForecast.add("15");
        timeForecast.add("16");
        timeForecast.add("17");
        timeForecast.add("11");
        timeForecast.add("12");
        timeForecast.add("13");
        timeForecast.add("14");
        timeForecast.add("15");
        timeForecast.add("16");
        timeForecast.add("17");

        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");
        tempForecast.add("31");

        iconForecast.add("01d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("11d");
        iconForecast.add("01d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("11d");
        iconForecast.add("01d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("10d");
        iconForecast.add("11d");
    }

    public void onItemClick(View view,int position) {
        Toast.makeText(view.getContext(), "You clicked " + myRecyclerViewAdapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();

    }

}
