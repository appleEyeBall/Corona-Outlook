package com.tisecodes.corona.outlook.Others;

import com.tisecodes.corona.outlook.Model.DayWeatherData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Oluwatise on 5/5/2017.
 */

/* This class gets the minimum and maximum temperatures
from an array of temperatures for that day*/
public class GetMinMax {
    private ArrayList<DayWeatherData> list = new ArrayList<>();
    private ArrayList<Integer> listOfMaxNumbers = new ArrayList();
    private ArrayList<Integer> listOfMinNumbers = new ArrayList();

    public GetMinMax(ArrayList<DayWeatherData> list) {
        this.list = list;
        for ( DayWeatherData data: list ) {
            listOfMaxNumbers.add(Integer.parseInt(data.getTemp_max()));
            listOfMinNumbers.add(Integer.parseInt(data.getTemp_min()));
        }
    }

    public Double getMin() {
        int rawMin = Collections.min(listOfMinNumbers);
        return (double) rawMin;
    }

    public Double getMax(){
        int rawMax = Collections.max(listOfMaxNumbers);
        return (double) rawMax;
    }
}