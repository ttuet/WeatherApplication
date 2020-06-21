package com.example.weatherapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.ViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.adapter.SearchCityAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
                String s = editText.getText().toString();
                setAdapter(s);


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
    }

    private void setAdapter( String searchedString){



    }
}
