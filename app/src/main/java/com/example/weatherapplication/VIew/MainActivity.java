package com.example.weatherapplication.VIew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.Model.LoadWeatherWorker;
import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.ViewPagerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ImageView iconCity;
    TextView cityName;
    List<City> cityList;
//    TabLayout tabLayout;
//    TabItem tab_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();


        createViewPager(viewPager);
//
        SharedPreferences pre = getApplicationContext().getSharedPreferences("weather",MODE_PRIVATE);
        if(pre.getInt("number",0)==0) {

            Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
            PeriodicWorkRequest.Builder myWorkBuilder = new PeriodicWorkRequest.Builder(LoadWeatherWorker.class, 15, TimeUnit.MINUTES)
                    .setConstraints(constraints);
            PeriodicWorkRequest myWork = myWorkBuilder.build();
            WorkManager.getInstance(MainActivity.this).enqueue(myWork);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                cityName.setText(cityList.get(position).getCity());
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iconCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivity(intent);
            }
        });



    }

    private void addControl() {
        viewPager = findViewById(R.id.view_pager);
        iconCity = findViewById(R.id.iconcity);
        cityName = findViewById(R.id.name_city);
//        tabLayout = findViewById(R.id.tab_layout);
    }
    private void addEvent() {
    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        SharedPreferences preCity = getApplicationContext().getSharedPreferences("cityList",MODE_PRIVATE);
        String jsonCity = preCity.getString("json","i");
        Gson gson = new Gson();
        Type listCityType = new TypeToken<List<City>>(){}.getType();
        Log.i("test", "doWork: "+jsonCity);
         cityList = gson.fromJson(jsonCity,listCityType);




        for(int i= 0;i<cityList.size();i++){
            Log.d("WWW", "createViewPager: "+cityList.get(i).getCity() );
            adapter.addFrag(new WeatherFragment(cityList.get(i).getId()));
        }


        viewPager.setAdapter(adapter);
    }


}
