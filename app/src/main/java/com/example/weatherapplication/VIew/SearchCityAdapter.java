package com.example.weatherapplication.VIew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;
import com.example.weatherapplication.viewmodel.WorkMangerController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.view.View.GONE;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder>  {
    List<City> cityList;

    private LayoutInflater inflater;
    private Context mContext;

    public SearchCityAdapter(Context context, List<City> cityList) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.cityList = cityList;
    }
    public SearchCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city_list, parent, false);
        return new SearchCityAdapter.ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull SearchCityAdapter.ViewHolder holder, int position) {
        holder.desc.setVisibility(GONE);
        holder.cityName.setText(cityList.get(position).getCity());
        holder.Country.setText(cityList.get(position).getCountry());
        holder.Country.setTextSize(15);
        holder.cityName.setTextSize(20);

    }
    public int getItemCount() {
        return cityList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cityName;
        TextView Country;
        TextView desc;
//        private ItemClickListener itemClickListener;
        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.name_);
            Country = itemView.findViewById(R.id.temp);
            desc = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int p = getLayoutPosition();
                    SharedPreferences preCity = mContext.getSharedPreferences("cityList", Context.MODE_PRIVATE);
                    String jsonListCity = preCity.getString("json", "i");
                    if (!jsonListCity.equalsIgnoreCase("i")) {
                        Gson gson = new Gson();
                        Type listCityType = new TypeToken<List<City>>() {
                        }.getType();

                        List<City> cityList2 = gson.fromJson(jsonListCity, listCityType);
                        cityList2.add(cityList.get(p));
                        String jsonn = gson.toJson(cityList2);
                        preCity.edit().putString("json", jsonn).apply();
                    }
                    Toast.makeText(mContext,"Thêm thành công",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
