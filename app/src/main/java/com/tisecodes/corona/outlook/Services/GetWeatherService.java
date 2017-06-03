package com.tisecodes.corona.outlook.Services;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.Fragments.FiveDayWeatherFragment;
import com.tisecodes.corona.outlook.Model.DayWeatherData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by Oluwatise on 4/24/2017.
 */

public class GetWeatherService {
    private static final GetWeatherService ourInstance = new GetWeatherService();

    public static GetWeatherService getInstance() {
        return ourInstance;
    }


    private String unit;
    private MainActivity mainActivity = new MainActivity().getMainActivity();
    private FiveDayWeatherFragment fiveDayWeatherFragment = new FiveDayWeatherFragment();
    public void getWeatherUpdates(double lat, double lng, final String unit, String key, final RecyclerView rv, final CardView cv, final Animation bounce) {
        // show the loading spinner
        mainActivity.showSpinner();
        Log.v("BAMBI", "spinner shown");
        Log.v("BAMBI", "service called");
        final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?";
        String URL = BASE_URL+"lat="+lat+"&lon="+lng+"&units="+unit+"&APPID="+key;
        // If the user entered a city
        if (!MainActivity.getMainActivity().getCity().equals("")) {
            URL = BASE_URL+"q="+MainActivity.getMainActivity().getCity()+"&units="+unit+"&APPID="+key;
        }
        else {
            Log.v("BAMBI", "City is empty");
        }
        Log.v("BAMBI", URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //always create new weatherList, so we don't append
                    LinkedHashMap<String, ArrayList<DayWeatherData>> mapOfLists = new LinkedHashMap<>();
//                    ArrayList<ArrayList<DayWeatherData>> listOfLists = new ArrayList<>();
                    ArrayList<DayWeatherData> firstDayList = new ArrayList<>();
                    ArrayList<DayWeatherData> nextDayList = new ArrayList<>();
                    ArrayList<DayWeatherData> thirdDayList = new ArrayList<>();
                    ArrayList<DayWeatherData> fourthDayList = new ArrayList<>();
                    ArrayList<DayWeatherData> fifthDayList = new ArrayList<>();
                    long todayDateInMonth = getTodayTimeStamp();
                    long millisecInDay = 86400000;
                    String city = response.getJSONObject("city").getString("name");
                    String country = response.getJSONObject("city").getString("country");
                    String place = city+", "+country;           //e.g, Chicago,US
                    JSONArray listOfWeather = response.getJSONArray("list");
                    DayWeatherData dayWeatherData;
                    for (int i=0; i<listOfWeather.length(); i++) {
                        JSONObject dayWeatherObject = listOfWeather.getJSONObject(i);
                        int dt = dayWeatherObject.getInt("dt");
                        JSONObject mainObject = dayWeatherObject.getJSONObject("main");
                        JSONObject windObject = dayWeatherObject.getJSONObject("wind");
                        JSONObject weatherObject = dayWeatherObject.getJSONArray("weather").getJSONObject(0);
                        double temp =mainObject.getDouble("temp");
                        double temp_min = mainObject.getDouble("temp_min");
                        double temp_max = mainObject.getDouble("temp_max");
                        int humidity = mainObject.getInt("humidity");
                        double windSpeed =windObject.getDouble("speed");
                        String descriptionMain = weatherObject.getString("main");
                        String descriptionText = weatherObject.getString("description");
                        int descriptionID = weatherObject.getInt("id");

                        //Check what day the weather object belongs to, and put it in it's respectiveArrayList
                        //(firstDay, nextDay, thirdDay....)
                        dayWeatherData = new DayWeatherData(temp_min,temp_max,temp,humidity, windSpeed ,dt,descriptionText, descriptionID);
                        if (dayWeatherData.getDt()>=todayDateInMonth && dayWeatherData.getDt()< (todayDateInMonth+millisecInDay)) {
                            firstDayList.add(dayWeatherData);
                        }
                        else if (dayWeatherData.getDt()>=(todayDateInMonth+millisecInDay) && dayWeatherData.getDt()< (todayDateInMonth+(millisecInDay*2))) {
                            nextDayList.add(dayWeatherData);
                        }
                        else if (dayWeatherData.getDt()>=(todayDateInMonth+(millisecInDay*2)) && dayWeatherData.getDt()< (todayDateInMonth+(millisecInDay*3))) {
                            thirdDayList.add(dayWeatherData);
                        }
                        else if (dayWeatherData.getDt()>=(todayDateInMonth+(millisecInDay*3)) && dayWeatherData.getDt()< (todayDateInMonth+(millisecInDay*4))) {
                            fourthDayList.add(dayWeatherData);
                        }
                        else if (dayWeatherData.getDt()>=(todayDateInMonth+(millisecInDay*4)) && dayWeatherData.getDt()< (todayDateInMonth+(millisecInDay*5))) {
                            fifthDayList.add(dayWeatherData);
                        }
                    }
                    // add all the lists to listOfLists
                    /*I used a, b, c ... at the beginning so
                    that linkedHashMap will automatically order it alphabetically*/
                    mapOfLists.put("firstDayList", firstDayList);
                    mapOfLists.put("nextDayList", nextDayList);
                    mapOfLists.put("thirdDayList", thirdDayList);
                    mapOfLists.put("fourthDayList", fourthDayList);
                    mapOfLists.put("fifthDayList", fifthDayList);
                    fiveDayWeatherFragment.updateUI(place, mapOfLists, rv, cv, bounce);          //send the result to the Activity
                }
                catch (org.json.JSONException e) {
                    e.printStackTrace();
                    Log.v("BAMBI",  e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("BAMBI", "BIG ERROR "+error.getMessage());

            }
        });
        Volley.newRequestQueue(mainActivity).add(jsonObjectRequest);

    }

    public int getDateInMonth(long timestamp) {
        //Get the current day, in month
        Date date = new Date(timestamp);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("d");
        String rawDate = sdf.format(date);
        return Integer.parseInt(rawDate);
    }

    public long getTodayTimeStamp() {
        /* Get the timestamp of today, 12 am*/
        Date date = new Date();
        java.text.SimpleDateFormat sdfDay = new java.text.SimpleDateFormat("dd");
        java.text.SimpleDateFormat sdfMonth = new java.text.SimpleDateFormat("MMM");
        java.text.SimpleDateFormat sdfYear = new java.text.SimpleDateFormat("yyyy");
        // get the sdf using our own hour, minute and second values
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String day = sdfDay.format(date);
        String month = sdfMonth.format(date);
        String year = sdfYear.format(date);
        // Get the current date in Date format
        try {
            Date d = sdf.parse(day+" "+month+" "+year+" "+"00:"+"00:"+"00");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            Log.v("BAMBI", "error"+ e.toString());
        }
        return 0;
    }

}