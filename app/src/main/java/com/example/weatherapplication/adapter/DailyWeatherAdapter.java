package com.example.weatherapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    private List<String> timeList;
    private List<String> temperatureList;
    private List<String> iconList;
    private LayoutInflater inflater;
    private Context mContext;
    private DailyWeatherAdapter.ItemClickListener mClickListener;
    public DailyWeatherAdapter(Context context, List<String> timeList, List<String> temperatureList, List<String> iconList) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.timeList= timeList;
        this.iconList = iconList;
        this.temperatureList = temperatureList;
    }
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_daily, parent, false);
        return new DailyWeatherAdapter.ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.ViewHolder holder, int position) {
        String time = timeList.get(position);
        String temp = temperatureList.get(position);
        String icon = iconList.get(position);
        holder.time.setText(time);
        holder.temperature.setText(temp);
        String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";

        Glide.with(mContext).load(iconUrl)

                .into(holder.iconWeather);


    }
    public int getItemCount() {
        return timeList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time;
        TextView temperature;
        ImageView iconWeather;

        ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.daily_dt);
            temperature = itemView.findViewById(R.id.daily_tempmin);
            iconWeather = itemView.findViewById(R.id.daily_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }
    public String getItem(int id) {
        return timeList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(DailyWeatherAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
