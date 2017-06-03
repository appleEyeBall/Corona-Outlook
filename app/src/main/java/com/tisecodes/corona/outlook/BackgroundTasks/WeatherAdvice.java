package com.tisecodes.corona.outlook.BackgroundTasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.Model.DayWeatherData;

import java.util.ArrayList;

/**
 * Created by Oluwatise on 5/5/2017.
 */

public class WeatherAdvice extends AsyncTask<Void, Void, ArrayList<String>> {
    ArrayList<String> listOfAdvices;
    private int temp_now;
    private int temp_later;
    private int wind_now;
    private int wind_later;
    private boolean hasLaters = false; //if the there are later temps
    private TextView advicesView;
    String unit = MainActivity.getMainActivity().getUnit();
    double x=0;

    public WeatherAdvice(ArrayList<DayWeatherData> list, TextView advicesView) {
        this.advicesView = advicesView;
        temp_now = Integer.parseInt(list.get(0).getTemp());
        wind_now = Integer.parseInt(list.get(0).getWindSpeed());
        if (list.size()>1) {
            temp_later = Integer.parseInt(list.get(1).getTemp());
            wind_later = Integer.parseInt(list.get(1).getWindSpeed());
            hasLaters = true;
        }
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        if (unit == "imperial") {
            x=1;
        }
        else {
            x=0.56;
        }
         listOfAdvices = new ArrayList<>();
        if (hasLaters = true) {
            if (temp_later-temp_now>=5*x) {
                String advice = "It'll be hotter soon, lose the Jackets";
                listOfAdvices.add(advice);
            }
            if (temp_now - temp_later>=5*x) {
                String advice = "It'll be cooler soon, jackets on";
                listOfAdvices.add(advice);

            }
            if (wind_later - wind_now>=3) {
                String advice = "It'll be windier, soon";
                listOfAdvices.add(advice);

            }
            if (wind_now - wind_later>=3) {
                String advice = "Expect less wind";
                listOfAdvices.add(advice);
            }
            else {
                String advice = "Weather will pretty much be the same";
                listOfAdvices.add(advice);
            }
        }
        else {
            String advice = "Weather will pretty much be the same";
            listOfAdvices.add(advice);
        }
        return listOfAdvices;
    }

    @Override
    protected void onPostExecute(final ArrayList<String> listOfAdvices) {
        super.onPostExecute(listOfAdvices);

        advicesView.setText(listOfAdvices.get(0));
        advicesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listOfAdvices.size()>1) {
                    if (advicesView.getText() == listOfAdvices.get(0)) {
                        advicesView.setText(listOfAdvices.get(1));
                    }
                    else {
                        advicesView.setText(listOfAdvices.get(0));
                    }
                }

            }
        });

    }
}
