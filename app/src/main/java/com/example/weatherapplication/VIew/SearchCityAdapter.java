package com.example.weatherapplication.VIew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.Model.City;
import com.example.weatherapplication.R;

import java.util.List;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder>  {
    List<City> cityList;

    private LayoutInflater inflater;
    private Context mContext;
    private SearchCityAdapter.ItemClickListener mClickListener;
    public SearchCityAdapter(Context context, List<City> cityList) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.cityList = cityList;
    }
    public SearchCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_daily, parent, false);
        return new SearchCityAdapter.ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull SearchCityAdapter.ViewHolder holder, int position) {
        String city_name = cityList.get(position).getCity()+", "+cityList.get(position).getCountry();
        holder.cityName.setText(city_name);


    }
    public int getItemCount() {
        return cityList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityName;

        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.name_city);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }
    public String getItem(int id) {
        return cityList.get(id).getCity();
    }

    // allows clicks events to be caught
    public void setClickListener(SearchCityAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
