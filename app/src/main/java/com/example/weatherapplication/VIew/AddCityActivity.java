package com.example.weatherapplication.VIew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.Utils;
import com.example.weatherapplication.adapter.SearchCityAdapter;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class AddCityActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    ImageView icon_Back;

    ArrayList<City> cityArrayList;
    SearchCityAdapter searchCityAdapter;
    ImageButton btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        addControl();

        Intent intent= getIntent();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadCityJson();


            }


        });
        icon_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




    private void downloadCityJson() {


        try{
          String jsonListCity = Utils.getJsonFromAssets(getApplicationContext(),"list-city-vn.json");
          Log.i("data", jsonListCity);
          SharedPreferences  cityPre = getApplicationContext().getSharedPreferences("cityList",MODE_PRIVATE);


          cityPre.edit().putString("json",jsonListCity).commit();


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    private void addControl() {
        recyclerView = findViewById(R.id.search_city_recycler);
        editText = findViewById(R.id.edt_search);
        icon_Back = findViewById(R.id.icon_back);
        cityArrayList = new ArrayList<>();
        btnSearch = findViewById(R.id.btn_search);
    }

    private void setAdapter( String searchedString){



    }
}
