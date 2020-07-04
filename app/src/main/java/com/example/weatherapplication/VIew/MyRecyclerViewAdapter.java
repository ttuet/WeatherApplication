package com.example.weatherapplication.VIew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.Model.Temp;
import com.example.weatherapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Temp> tempList;
    private LayoutInflater inflater;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, List<Temp> tempList) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.tempList = tempList;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String time = tempList.get(position).getTime();
        String temp = tempList.get(position).getTemp();
        String icon = tempList.get(position).getIcon();
        long timeL = Long.parseLong(time) * (long) 1000;
        Date date = new Date(timeL);
        Locale locale = new Locale("vi");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm ",locale);
        format.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        holder.time.setText(format.format(date));
        holder.temperature.setText(Math.round(Double.parseDouble(temp))+"°");
        String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";

        Glide.with(mContext).load(iconUrl)

                .into(holder.iconWeather);


    }
    public int getItemCount() {
        return tempList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView time;
        TextView temperature;
        ImageView iconWeather;

        ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_forecast);
            temperature = itemView.findViewById(R.id.temp_forecast);
            iconWeather = itemView.findViewById(R.id.ic_weather_forecast);

        }


    }
    public String getItem(int id) {
        return tempList.get(id).getTime();
    }






}


