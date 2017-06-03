package com.tisecodes.corona.outlook.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tisecodes.corona.outlook.Model.DayWeatherData;
import com.tisecodes.corona.outlook.R;
import com.tisecodes.corona.outlook.ViewHolders.WeatherRecyclerVH;

import java.util.ArrayList;

/**
 * Created by Oluwatise on 4/28/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherRecyclerVH> {
    ArrayList<DayWeatherData> weatherList = new ArrayList<>();

    public WeatherAdapter(ArrayList<DayWeatherData> weatherDatas) {
        this.weatherList = weatherDatas;
    }

    @Override
    public WeatherRecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent, false);
        WeatherRecyclerVH weatherRecyclerVH = new WeatherRecyclerVH(v);
        return weatherRecyclerVH;
    }

    @Override
    public void onBindViewHolder(WeatherRecyclerVH holder, int position) {
        boolean tomorrow = false;

        DayWeatherData dayWeatherData = weatherList.get(position);
        int imageResource = R.drawable.sun2;
        if (position==0) {
            tomorrow = true;
        }
        holder.updateUI(tomorrow,dayWeatherData);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
