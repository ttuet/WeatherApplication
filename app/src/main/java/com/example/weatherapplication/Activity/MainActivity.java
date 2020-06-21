package com.example.weatherapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapplication.Model.LoadWeatherWorker;
import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.ViewPagerAdapter;
import com.example.weatherapplication.fragment.WeatherFragment;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    ImageView iconCity;
//    TabLayout tabLayout;
//    TabItem tab_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();


        createViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest.Builder myWorkBuilder = new PeriodicWorkRequest.Builder(LoadWeatherWorker.class,60, TimeUnit.MINUTES)
                                                                            .setConstraints(constraints);
        PeriodicWorkRequest myWork = myWorkBuilder.build();
        WorkManager.getInstance(MainActivity.this).enqueue(myWork);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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
//        tabLayout = findViewById(R.id.tab_layout);
    }
    private void addEvent() {
    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new WeatherFragment("Hà Nội"));


        viewPager.setAdapter(adapter);
    }


}
