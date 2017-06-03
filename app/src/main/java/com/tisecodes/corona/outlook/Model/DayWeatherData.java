package com.tisecodes.corona.outlook.Model;

import com.tisecodes.corona.outlook.R;

import java.io.Serializable;

/**
 * Created by Oluwatise on 4/27/2017.
 */

public class DayWeatherData implements Serializable{
    private String temp_min;
    private String temp_max;
    private String temp;
    private String humidity;
    private String windSpeed;
    private long dt;            //timeStamp
    private String descriptionText;
    private int descriptionID;
    private int imageResource;

    public DayWeatherData(double temp_min, double temp_max, double temp, int humidity, double windSpeed, int dt, String descriptionText, int descriptionID) {
        int rawTemp_min  = (int) temp_min;  this.temp_min = String.valueOf(rawTemp_min);
        int rawTemp_max = (int) temp_max;   this.temp_max = String.valueOf(rawTemp_max);
        int rawTemp = (int) temp;   this.temp = String.valueOf(rawTemp);
        this.humidity = String.valueOf(humidity);
        int rawWindSpeed = (int) windSpeed; this.windSpeed = String.valueOf(rawWindSpeed);
        this.dt = (long)dt*1000;
        this.descriptionText = descriptionText;
        this.descriptionID = descriptionID;

        if (this.descriptionID >=200 && this.descriptionID<321){
            imageResource = R.drawable.small_storm;
        }
        else if (this.descriptionID >=500 && this.descriptionID<531){
            imageResource = R.drawable.small_rain;
        }
        else if (this.descriptionID >=600 && this.descriptionID<610){
            imageResource = R.drawable.small_snow;
        }
        else if (this.descriptionID == 800){
            imageResource = R.drawable.small_sun;
        }
        else if (this.descriptionID >=801 && this.descriptionID<804){
            imageResource = R.drawable.small_cloud;
        }
        else if (this.descriptionID ==900){
            imageResource = R.drawable.tornado;
        }
        else if (this.descriptionID ==905){
            imageResource = R.drawable.small_wind;
        }
        else {
            imageResource = R.drawable.small_sun;
        }
    }

    public String getTemp_min() {
        return this.temp_min;
    }

    public String getTemp_max() {
        return this.temp_max;
    }

    public String getTemp() {
        return this.temp;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public long getDt() {
        return this.dt;
    }

    public String getDescriptionText() {
        return this.descriptionText;
    }

    public int getDescriptionID() {
        return this.descriptionID;
    }
    public String getWindSpeed() {
        return windSpeed;
    }
    public int getImageResource() {
        return imageResource;
    }
}
