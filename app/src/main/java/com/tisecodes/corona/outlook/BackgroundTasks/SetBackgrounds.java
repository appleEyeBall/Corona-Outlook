package com.tisecodes.corona.outlook.BackgroundTasks;

import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.tisecodes.corona.outlook.Activities.MainActivity;
import com.tisecodes.corona.outlook.R;

/**
 * Created by Oluwatise on 5/7/2017.
 */

public class SetBackgrounds extends AsyncTask<Void, Void, Void> {
    MainActivity mainActivity = MainActivity.getMainActivity();
    Window window = mainActivity.getWindow();
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    EditText searchCityView;
    double temp;
    String unit = mainActivity.getUnit();
    int resource;   //stores the appBar's color resource
    // x and y are used for conversion of unit
    int x;
    double y;

    public SetBackgrounds(AppBarLayout appBarLayout, Toolbar toolbar, EditText searchCityView, String temp) {
        this.appBarLayout = appBarLayout;
        this.toolbar = toolbar;
        this.temp = Double.parseDouble(temp);
        this.searchCityView = searchCityView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (unit.equals("imperial")) {
            x=0;
            y=1;
        }
        else {
            x=32;
            y=0.556;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        /* Make the toolbar color transparent, when appBar is expanded,
        and same color with appBar, when collapsed */
        final Animation show = AnimationUtils.loadAnimation(mainActivity, R.anim.show);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                window.setStatusBarColor(mainActivity.getResources().getColor(resource, null));
                int height = appBarLayout.getHeight();
                if (verticalOffset == 0) {
                    toolbar.setBackgroundResource(R.color.transparent);
                    searchCityView.setVisibility(View.GONE);
                }
                else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    toolbar.setBackgroundResource(resource);
                    searchCityView.startAnimation(show);
                    searchCityView.setVisibility(View.VISIBLE);
                }
                else if (verticalOffset<0) {
                    searchCityView.setVisibility(View.GONE);

                }
            }
        });

        // Now the temps.....
        Log.v("BAMBI", "In setBackgrounds, temp is "+String.valueOf(temp));
        Log.v("BAMBI", "In setBackgrounds, x is "+String.valueOf(x));
        Log.v("BAMBI", "In setBackgrounds, y is "+String.valueOf(y));
        Log.v("BAMBI", "In setBackgrounds, unit is "+String.valueOf(unit));
        if (temp >= (80-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x5Hot);
            resource = R.color.x5Hot;
        }
        else if (temp >= (70-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x4Hot);
            resource = R.color.x4Hot;

        }
        else if (temp >= (60-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x3Hot);
            resource = R.color.x3Hot;
        }
        else if (temp >= (50-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x2Hot);
            resource = R.color.x2Hot;
        }
        else if (temp >= (40-x)*y) {
            appBarLayout.setBackgroundResource(R.color.xHot);
            resource = R.color.xHot;
        }
        else if (temp >= (30-x)*y) {
            appBarLayout.setBackgroundResource(R.color.hot);
            resource = R.color.hot;
        }
        else if (temp >= (20-x)*y) {
            appBarLayout.setBackgroundResource(R.color.cold);
            resource = R.color.cold;
        }
        else if (temp >= (10-x)*y) {
            appBarLayout.setBackgroundResource(R.color.xCold);
            resource = R.color.xCold;
        }
        else if (temp >= (0-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x2Cold);
            resource = R.color.x2Cold;
        }
        else if (temp >= (-10-x)*y) {
            appBarLayout.setBackgroundResource(R.color.x3Cold);
            resource = R.color.x3Cold;
        }
        else {
            appBarLayout.setBackgroundResource(R.color.x4Cold);
            resource = R.color.x4Cold;
        }
    }
}
