package com.example.weatherapplication.VIew;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.Model.Temp;
import com.example.weatherapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ListCityAdapter extends RecyclerView.Adapter<ListCityAdapter.ViewHolder> {
    private List<String> cityList;
    private List<String> tempList;
    private List<String> descList;
    private LayoutInflater inflater;
    private Context mContext;
    //    private ListCityAdapter.ItemLongClickListener mLongClickListener;
    public ListCityAdapter(Context context, List<String> cityList,List<String> tempList,List<String> descList) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.tempList = tempList;
        this.cityList = cityList;
        this.descList = descList;
    }
    public ListCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city_list, parent, false);
        return new ListCityAdapter.ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull ListCityAdapter.ViewHolder holder, int position) {
        String city = cityList.get(position);
        String temp = tempList.get(position);
        String desc = descList.get(position);
        holder.cityName.setText(city);
        holder.temperature.setText(temp+"°");
        holder.description.setText(desc);

    }
    public int getItemCount() {
        return tempList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        int i=0;
        TextView cityName;
        TextView temperature;
        TextView description;
        ImageView delete ;
        ViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.name_);
            temperature = itemView.findViewById(R.id.temp);
            description = itemView.findViewById(R.id.desc);
            delete = itemView.findViewById(R.id.icon_delete);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int[] arrStatus = new int[]{0,8};
                    temperature.setVisibility(arrStatus[1-i]);
                    description.setVisibility(arrStatus[1-i]);
                    delete.setVisibility(arrStatus[i]);
                    i=1-i;
                    return false;
                }

            });
            delete.setOnClickListener(new View.OnClickListener() {
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
                        cityList2.remove(p);
                        String jsonn = gson.toJson(cityList2);
                        preCity.edit().putString("json", jsonn).apply();
                    }
                    Toast.makeText(mContext,"Xoá thành công"+ p,Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public String getItem(int id) {
        return tempList.get(id);
    }


}
