package com.tisecodes.corona.outlook.BackgroundTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.tisecodes.corona.outlook.Model.DayWeatherData;

/**
 * Created by Oluwatise on 5/5/2017.
 */

public class HeatIndex extends AsyncTask<Void, Void, String> {
    private double humidity;
    private int windSpeed;
    private int temperature;
    private int heatIndex;
    double x;
    double y;
    private TextView today_feel;
    String unit;
    public HeatIndex(DayWeatherData weatherData, TextView today_feel, String unit) {
        double rawHumidity = Double.parseDouble(weatherData.getHumidity());
        this.humidity = rawHumidity/100;
        this.temperature = Integer.parseInt(weatherData.getTemp());
        this.windSpeed = Integer.parseInt(weatherData.getWindSpeed());
        this.today_feel = today_feel;
        this.unit = unit;
        if (unit.equals("imperial")) {
            this.x = 0;
            this.y = 1;
        }
        else {
            this.x = 32;
            this.y = 0.556;
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        if (!this.unit.equals("imperial")) {
            temperature = (int) (temperature/0.556) +32;
        }
        if (temperature>80) {
            Log.v("BAMBI", "Unit is "+ this.unit);
            Log.v("BAMBI", "IT is more than 80");
            // when it is really hot
            double rawHeatIndex = -42.379 + (2.04901523*temperature)
                    + (10.14333127*humidity)- (0.22475541*temperature*humidity)
                    -(0.00683783*Math.pow(temperature, 2)) - (0.5481717*Math.pow(humidity,2))
                    +(0.001228748 *Math.pow(temperature, 2)*humidity)
                    + (0.00085282*temperature*Math.pow(humidity,2))
                    -(0.0000019*Math.pow(temperature, 2)*Math.pow(humidity,2));
            if (humidity<13 && temperature<112) {
                rawHeatIndex = rawHeatIndex-(((13-humidity)/4) * Math.sqrt((17-Math.abs(temperature-95))/17));
            }
            else if (humidity>85 && temperature<87) {
                rawHeatIndex = rawHeatIndex - ((humidity-85)/10) * ((87-temperature)/5);
            }
            // change the unit if necessary
            heatIndex = (int) rawHeatIndex;
            if (!this.unit.equals("imperial")) {
                heatIndex = (int) ((heatIndex -32) * 0.556);
            }
        }
        else if (temperature<40) {
            Log.v("BAMBI", "Unit is "+ this.unit);
            // You use wind chill in winter
            double partWindChill = 35.74 +(0.6215*temperature)
                    -(35.75*Math.pow(windSpeed, 0.16))
                    +(0.4275*temperature*Math.pow(windSpeed, 0.16));
            heatIndex = (int) partWindChill;
            if (!this.unit.equals("imperial")) {
                heatIndex = (int) ((heatIndex -32) * 0.556);
            }
        }
        else {
            // when temperature is in between
            double partHeatIndex = 0.5 *(temperature + 61 +((temperature-68)*1.2)+ (humidity*0.094));
            double rawHeatIndex = (partHeatIndex+temperature)/2;   //average it with the temp
            heatIndex = (int) rawHeatIndex;
            if (!this.unit.equals("imperial")) {
                heatIndex = (int) ((heatIndex -32) * 0.556);
            }
        }
        return String.valueOf(heatIndex);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        setHeatIndex(s);

    }

    private void setHeatIndex(String s) {
        today_feel.setText("Feels like "+ s+"\u00B0");
    }
}
