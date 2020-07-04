package com.example.weatherapplication.VIew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.weatherapplication.R;

public class ListCityActivity extends AppCompatActivity implements ListCityAdapter.ItemLongClickListener {
    RecyclerView listCityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        listCityView = findViewById(R.id.list_city_recycler);

    }


    @Override
    public void onItemLongClick(View view, int position) {

    }
}