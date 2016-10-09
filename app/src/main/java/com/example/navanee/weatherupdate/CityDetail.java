package com.example.navanee.weatherupdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CityDetail extends AppCompatActivity implements GetData.DataRetreiver {

    String city;
    String state;
    TextView locationDet;
    ListView weatherListView;
    LinearLayout weatherListLayout;
    RelativeLayout weatherLoadingLayout;
    ArrayList<Weather> weatherArrList = new ArrayList<Weather>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        setAllViews();
        city = getIntent().getExtras().get("city").toString();
        state = getIntent().getExtras().get("state").toString();
        String dataUrl = "http://api.wunderground.com/api/9ae0e4077f38595e/hourly/q/state_name/city_name.json";
        dataUrl = dataUrl.replace("state_name",state);
        dataUrl = dataUrl.replace("city_name",city);
        new GetData(this,city,state).execute(dataUrl);
    }

    public void setAllViews() {
        locationDet = (TextView) findViewById(R.id.locationDetails);
        weatherListView = (ListView) findViewById(R.id.weatherList);
        weatherListLayout = (LinearLayout) findViewById(R.id.weatherListLayout);
        weatherLoadingLayout = (RelativeLayout) findViewById(R.id.weatherLoadingLayout);
    }

    @Override
    public void setData(final ArrayList<Weather> weatherList) {
        weatherArrList = weatherList;
        if(weatherList.size() == 0) {
            Toast.makeText(this,R.string.toast_noRecordFound,Toast.LENGTH_LONG).show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(CityDetail.this,MainActivity.class);
                    startActivity(intent);
                }
            },5 * 1000);
        } else {
            WeatherAdapter adapter = new WeatherAdapter(this,R.layout.hourly_forecast_row_layout,weatherList);
            findViewById(R.id.weatherLoadingLayout).setVisibility(View.GONE);
            findViewById(R.id.weatherListLayout).setVisibility(View.VISIBLE);
            locationDet.setText(city + ", " + state);
            weatherListView.setAdapter(adapter);
            weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CityDetail.this,Forecast.class);
                    Weather selectedForecast = weatherList.get(position);
                    intent.putExtra("forecast",selectedForecast);
                    intent.putExtra("city",city);
                    intent.putExtra("state",state);
                    intent.putExtra("wList",weatherList);
                    startActivity(intent);
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addCity) {
            if(weatherArrList.size() > 0) {
                int index = isFavCityAlreadyExists(city, state);
                if(index == -1) {
                    MainActivity.favCities.add(new FavouriteCity(weatherArrList.get(0).getTimeStamp(), city, state, weatherArrList.get(0).getTemperature()));
                } else {
                    FavouriteCity fav = MainActivity.favCities.get(index);
                    fav.setTemperature(weatherArrList.get(0).getTemperature());
                    fav.setDate(weatherArrList.get(0).getTimeStamp());
                }
            }
        }
        return true;
    }

    public int isFavCityAlreadyExists(String city, String state) {
        int index = -1;
        for(int i = 0; i < MainActivity.favCities.size(); i++) {
            if(MainActivity.favCities.get(i).getCity() == city && MainActivity.favCities.get(i).getCity() == city) {
                index = i;
                break;
            }
        }
        return index;
    }
}
