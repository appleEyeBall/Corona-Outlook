package com.tisecodes.corona.outlook.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.Adapters.WeatherAdapter;
import com.tisecodes.corona.outlook.Model.DayWeatherData;
import com.tisecodes.corona.outlook.Others.GetMinMax;
import com.tisecodes.corona.outlook.R;
import com.tisecodes.corona.outlook.Services.GetWeatherService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiveDayWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiveDayWeatherFragment extends Fragment{


    public FiveDayWeatherFragment() {
        // Required empty public constructor
    }

    public static FiveDayWeatherFragment newInstance() {
        FiveDayWeatherFragment fragment = new FiveDayWeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    MainActivity mainActivity = MainActivity.getMainActivity();
    RecyclerView weatherRecycler;
    CardView cv;
    Animation bounce;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            String unit = savedInstanceState.getString("unit");
            MainActivity.getMainActivity().setUnit(unit);
            Log.v("BAMBI", "in Oncreate, the unit is "+MainActivity.getMainActivity().getUnit());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("BAMBI", "in leaving, the unit is "+MainActivity.getMainActivity().getUnit());
        outState.putString("unit", MainActivity.getMainActivity().getUnit());
//        outState.putString("city", MainActivity.getMainActivity().getCity());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("BAMBI", "on Pause called ");
        // new view will overlap the previous one
        // if the previous one isn't set to 'GONE'
        this.cv.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_five_day_weather, container, false);
         /********** We sent the the recycler view, cardView
         and animation "bounce" to the service
         so that they can be remembered when it gets back ************/

        // I am doing the animation after all data is
        // loaded. That is why I am sending it
        weatherRecycler = (RecyclerView) v.findViewById(R.id.weatherRecycler);

        cv = (CardView) v.findViewById(R.id.cardView);
        bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        GetWeatherService.getInstance().getWeatherUpdates(mainActivity.getLatitude(), mainActivity.getLongitude(), mainActivity.getUnit(), mainActivity.getKEY(), weatherRecycler, cv, bounce);
        return v;
    }

    public void updateUI(String place, LinkedHashMap<String, ArrayList<DayWeatherData>> mapOfLists, RecyclerView rv, CardView cv, Animation bounce) {
        mainActivity.hideSpinner(); //Hide the loading spinner
        MainActivity.getMainActivity().updateUI(place, mapOfLists.get("firstDayList"));
        ArrayList<DayWeatherData> weatherList = new ArrayList<>();
        /* Remove the first item,
         which was used in d activity for "today"
        and not needed for this fragment, which forecasts for other days*/
        mapOfLists.remove("firstDayList");
        for (ArrayList<DayWeatherData> list :mapOfLists.values()) {
            /*Each list reps each of d remaining 4 days. so just pick the first.
            and create a new object from it with updated temps*/
            DayWeatherData weatherData = list.get(0);
            // get the overall max and min for that day from 'list'
            GetMinMax getMinMax = new GetMinMax(list);
            Double overallMin = getMinMax.getMin();
            Double overallMax = getMinMax.getMax();
            Double temp = Double.valueOf(weatherData.getTemp());
            int humidity = Integer.parseInt(weatherData.getHumidity());
            double windSpeed = Double.parseDouble(weatherData.getWindSpeed());
            int date = (int) (weatherData.getDt()/1000);
            String text = weatherData.getDescriptionText();
            int descriptionID = weatherData.getDescriptionID();
            //Create a new object with updated values.
            // we shouldn't alter the object gotten from the service
            DayWeatherData weatherDataObject = new DayWeatherData(overallMin,overallMax,temp,humidity, windSpeed, date, text,descriptionID);
            weatherList.add(weatherDataObject);
        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        // put the list of weather in the adapter
        /* Note, in this Fragment, 'weatherDataObject' is created from 'weatherData'
        * which is sent to the adapter of the recyclerView. 'weatherData' and 'mapOflists'
        * are still very much intact,and can be used for future manipulation */
        weatherRecycler = rv;
        WeatherAdapter adapter = new WeatherAdapter(weatherList);
        Log.v("BAMBI", "min is " +weatherList.get(0).getTemp_min());
//        Set values for weather Recycler
        weatherRecycler.setLayoutManager(llm);
        weatherRecycler.setHasFixedSize(true);
        weatherRecycler.setAdapter(adapter);

        // I like to call the animation after the data is loaded.....
        cv.startAnimation(bounce);
        cv.setVisibility(View.VISIBLE);
        /* Scroll the taskBar only the first time*/
        if (MainActivity.getMainActivity().isFirstTimeLoaded()) {
            bounce.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    MainActivity.getMainActivity().expandTaskBar();
                    // Do not do this after the first time the app is loaded
                    MainActivity.getMainActivity().setFirstTimeLoaded(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
}
