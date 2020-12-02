package com.example.weatherapplication.VIew;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.WorkMangerController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {


    ViewPager viewPager;
    ImageView iconCity;
    ImageView iconSetting;
    TextView cityName;
    List<City> cityList;
    boolean checkGPS = false;
    SharedPreferences preCity;
    SharedPreferences glVari;
    Gson gson;
    Location currentLocation;
    LocationManager locationManager;
    ViewPagerAdapter adapter;
    WorkMangerController mangerController;
    SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    //    TabLayout tabLayout;
//    TabItem tab_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        getLocation();
        preCity = getApplicationContext().getSharedPreferences("cityList", MODE_PRIVATE);
        glVari = getApplicationContext().getSharedPreferences("Vari",MODE_PRIVATE);
        glVari.edit().putString("doC", "°C").putInt("periodic", 1).apply();
        Log.i("MAin", "onCreate: "+ glVari.getString("doC","°C"));

        onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                mangerController.cancelWork();
                int time = glVari.getInt("periodic",1);
                mangerController.setWork(time);
                createViewPager(viewPager);
            }
        };
        glVari.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                mangerController.cancelWork();
                int time = glVari.getInt("periodic",1);
                mangerController.setWork(time);
            }
        });
        preCity.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        createViewPager(viewPager);
        mangerController = new WorkMangerController(getApplicationContext());
        SharedPreferences pre = getApplicationContext().getSharedPreferences("Weather",MODE_PRIVATE);
        String jso = pre.getString("json","i");
        if(jso.equalsIgnoreCase("i")) {
            int time = glVari.getInt("periodic",1);
            mangerController.setWork(time);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                cityName.setText(cityList.get(position).getCity());

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iconCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListCityActivity.class);
                startActivity(intent);

            }
        });
        iconSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,60000,100,this);
    }

    private void addControl() {
        viewPager = findViewById(R.id.view_pager);
        iconCity = findViewById(R.id.iconcity);
        iconSetting = findViewById(R.id.iconsetting);
        cityName = findViewById(R.id.name_city);
        cityList = new ArrayList<City>();
        gson = new Gson();


    }



    private void updateList(){
        cityList.clear();
        String jsonCity = preCity.getString("json", "i");
        if (!jsonCity.equalsIgnoreCase("i")) {

            Type listCityType = new TypeToken<List<City>>() {
            }.getType();

            cityList = gson.fromJson(jsonCity, listCityType);
        }
    }
    private void createViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        updateList();

        for (int i = 0; i < cityList.size(); i++) {

            adapter.addFrag(new WeatherFragment(cityList.get(i).getId()));
        }
        viewPager.setAdapter(adapter);
    }




    @Override
    public void onLocationChanged(Location location) {
        Log.i("Main", "onLocationChanged: lat:" + location.getLatitude() + ", lon: " + location.getLongitude());
        String jsonCity = preCity.getString("json", "i");
        if (jsonCity.equalsIgnoreCase("i")) {
            cityList = new ArrayList<City>();
            City current = new City("1", "Vị trí hiện tại", "Việt Nam", location.getLatitude() + "", location.getLongitude() + "");
            cityList.add(current);
            String jsonCityList = gson.toJson(cityList);
            preCity.edit().putString("json", jsonCityList).apply();
        } else {
            Type listCityType = new TypeToken<List<City>>() {
            }.getType();
            cityList = gson.fromJson(jsonCity, listCityType);
            cityList.get(0).setLat(location.getLatitude() + "");
            cityList.get(0).setLon(location.getLongitude() + "");
            String jsonCityList = gson.toJson(cityList);
            preCity.edit().putString("json", jsonCityList).apply();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
