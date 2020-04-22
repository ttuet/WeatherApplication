package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.weatherapplication.adapter.ViewPagerAdapter;
import com.example.weatherapplication.fragment.LocationFragment;
import com.example.weatherapplication.fragment.SettingFragment;
import com.example.weatherapplication.fragment.WeatherFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    TabItem tab_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();

        createViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));





    }

    private void addControl() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }
    private void addEvent() {
    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new WeatherFragment());
        adapter.addFrag(new LocationFragment());
        adapter.addFrag(new SettingFragment());

        viewPager.setAdapter(adapter);
    }
}
