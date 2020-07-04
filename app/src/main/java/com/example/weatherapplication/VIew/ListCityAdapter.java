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

public class ListCityAdapter extends RecyclerView.Adapter<ListCityAdapter.ViewHolder> {
    private List<String> cityList;
    private List<String> tempList;
    private List<String> descList;
    private LayoutInflater inflater;
    private Context mContext;
    private ListCityAdapter.ItemLongClickListener mLongClickListener;
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView cityName;
        TextView temperature;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.name_);
            temperature = itemView.findViewById(R.id.temp);
            description = itemView.findViewById(R.id.desc);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onItemLongClick(view, getAdapterPosition());
            return false;
        }

//        @Override
//        public void onLongClick(View view) {
//            if (mLongClickListener != null) mLongClickListener.onItemLongClick(view, getAdapterPosition());
//
//        }
    }
    public String getItem(int id) {
        return tempList.get(id);
    }

    // allows clicks events to be caught
    public void setmLongClickListener(ListCityAdapter.ItemLongClickListener itemLongClickListener) {
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
