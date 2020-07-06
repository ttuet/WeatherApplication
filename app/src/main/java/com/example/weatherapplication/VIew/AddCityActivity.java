package com.example.weatherapplication.VIew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.Utils;
import com.example.weatherapplication.viewmodel.WorkMangerController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddCityActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    EditText editText;
    ImageView icon_Back;
    ProgressDialog dialog;
    ArrayList<City> cityArrayList;
    SearchCityAdapter searchCityAdapter;
    ImageButton btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        addControl();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link = "http://thaongu.000webhostapp.com/api/read.php?name=" + editText.getText().toString();
                CityTask cityTask = new CityTask();
                cityTask.execute(link);
            }


        });
        icon_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addControl() {
        recyclerView = findViewById(R.id.search_city_recycler);
        editText = findViewById(R.id.edt_search);
        icon_Back = findViewById(R.id.icon_back);
        cityArrayList = new ArrayList<>();
        btnSearch = findViewById(R.id.btn_search);
        dialog = new ProgressDialog(AddCityActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Đang tải...");
        dialog.setCanceledOnTouchOutside(false);
    }
    class CityTask extends AsyncTask<String, Void, String> {
        public CityTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.equalsIgnoreCase("null")) {
                recyclerView.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                Type listCityType = new TypeToken<List<City>>() {
                }.getType();

                cityArrayList = gson.fromJson(s, listCityType);


                searchCityAdapter = new SearchCityAdapter(AddCityActivity.this, cityArrayList);
                recyclerView.setAdapter(searchCityAdapter);
                dialog.dismiss();
            }
            else {
                recyclerView.setVisibility(View.GONE);
                dialog.dismiss();
            }

        }
        @Override
        protected String doInBackground(String... strings) {
            String s = strings[0];
            String respone = null;
            try {
                respone = Utils.getJsonFromServer(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return respone;
        }

    }
}
