package com.tisecodes.corona.outlook.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisecodes.corona.outlook.Model.DayWeatherData;
import com.tisecodes.corona.outlook.R;

import java.util.Date;

/**
 * Created by Oluwatise on 4/28/2017.
 */

public class WeatherRecyclerVH extends RecyclerView.ViewHolder {
    ImageView day_Image;
    TextView day;
    TextView day_description;
    TextView day_temp_high;
    TextView day_temp_low;

    public WeatherRecyclerVH(View itemView) {
        super(itemView);
        day_Image = (ImageView) itemView.findViewById(R.id.day_image);
        day = (TextView) itemView.findViewById(R.id.day);
        day_description = (TextView) itemView.findViewById(R.id.day_description);
        day_temp_high = (TextView) itemView.findViewById(R.id.day_temp_high);
        day_temp_low = (TextView) itemView.findViewById(R.id.day_temp_low);
    }

    public void updateUI(boolean tomorrow, DayWeatherData dayWeatherData){
        day_description.setText(dayWeatherData.getDescriptionText());
        day_temp_high.setText(dayWeatherData.getTemp_max()+"\u00B0");
        day_temp_low.setText(dayWeatherData.getTemp_min()+"\u00B0");
        day_Image.setImageResource(dayWeatherData.getImageResource());

        String dayOfWeek = getDateDay(dayWeatherData.getDt());
        if (tomorrow==true) {
            day.setText(R.string.tomorrow);
        }
        else {
            day.setText(dayOfWeek);
        }
    }

    private String getDateDay(long timestamp){
        //Get the current date
        Date date = new Date(timestamp);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE");
        return sdf.format(date);
    }
}
