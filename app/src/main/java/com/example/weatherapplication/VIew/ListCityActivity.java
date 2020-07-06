package com.example.weatherapplication.VIew;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Operation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.Utils;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListCityActivity extends AppCompatActivity  {
    RecyclerView listCityView;
    ImageView iconBack;
    Button btnAddCity;
    ListCityAdapter listCityAdapter;
    List<String> nameList,tempList,descList;
    ProgressDialog dialog;
    SharedPreferences pre ;
    SharedPreferences preCity ;
    SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        listCityView = findViewById(R.id.list_city_recycler);
        iconBack = findViewById(R.id.icon_back);
        btnAddCity = findViewById(R.id.btn_addCity);
        dialog = new ProgressDialog(ListCityActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Đang tải...");
        dialog.setCanceledOnTouchOutside(false);
        pre = getApplicationContext().getSharedPreferences("Weather",MODE_PRIVATE);
        preCity = getApplicationContext().getSharedPreferences("cityList",MODE_PRIVATE);
        onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                nameList.clear();
                tempList.clear();
                descList.clear();
                mapvalue();
                listCityAdapter = new ListCityAdapter(ListCityActivity.this,nameList,tempList,descList);
                listCityView.setAdapter(listCityAdapter);
            }
        };
        preCity.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        addValue();
        mapvalue();
        listCityAdapter = new ListCityAdapter(ListCityActivity.this,nameList,tempList,descList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listCityView.setLayoutManager(linearLayoutManager);
        listCityView.setAdapter(listCityAdapter);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListCityActivity.this, AddCityActivity.class);
               startActivity(intent);

            }
        });
    }

private  void mapvalue(){
    String jsonCity = preCity.getString("json","i");
    Gson gson = new Gson();
    Type listCityType = new TypeToken<List<City>>() {
    }.getType();

    List<City> cityList = gson.fromJson(jsonCity, listCityType);

    for(int i=0;i<cityList.size();i++){
        String result = pre.getString(cityList.get(i).getId(),"i");
        JSONObject jsonObject = null;
        try {
                jsonObject = new JSONObject(result);
                JSONObject current = jsonObject.getJSONObject("current");
                String temp = current.getString("temp");
                JSONArray weather = current.getJSONArray("weather");
                String desc = weather.getJSONObject(0).getString("description");
                nameList.add(cityList.get(i).getCity());
                tempList.add(Math.round(Double.parseDouble(temp))+"");
                descList.add(desc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
    private void addValue() {
        nameList = new ArrayList<String>();
        tempList = new ArrayList<String>();
        descList = new ArrayList<String>();
    }


}