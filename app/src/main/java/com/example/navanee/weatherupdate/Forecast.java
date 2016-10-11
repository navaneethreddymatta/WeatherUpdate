package com.example.navanee.weatherupdate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Forecast extends AppCompatActivity {
    TextView locationDetails, temperatureVal, cloudDescVal, minTempView, maxTempView, humidityVal, pressureVal, windsVal, feelsLikeVal, dewPointVal, cloudsVal;
    ImageView imgView;
    Weather forecast;
    String city, state, favCitiesStr;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    ArrayList<Weather> weatherList;
    int minTemp = 200, maxTemp = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();
    List<FavouriteCity> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        setAllViews();
        city = getIntent().getExtras().get("city").toString();
        state = getIntent().getExtras().get("state").toString();
        weatherList = (ArrayList<Weather>) getIntent().getExtras().get("wList");
        forecast = (Weather) getIntent().getExtras().get("forecast");
        getMinMaxTemp();
        setData();
        pref = getSharedPreferences(MainActivity.PREF_NAME,MODE_PRIVATE);
        editor = pref.edit();
        favCitiesStr = pref.getString(MainActivity.PREF_KEY_NAME,null);
        favList = gson.fromJson(favCitiesStr, MainActivity.type);
        getSupportActionBar().setTitle("City Weather");
    }

    public void setAllViews() {
        locationDetails = (TextView) findViewById(R.id.locationDetails);
        temperatureVal = (TextView) findViewById(R.id.temperatureView);
        cloudDescVal = (TextView) findViewById(R.id.cloudView);
        minTempView = (TextView) findViewById(R.id.minTempVal);
        maxTempView = (TextView) findViewById(R.id.maxTempVal);
        humidityVal = (TextView) findViewById(R.id.humidityVal);
        pressureVal = (TextView) findViewById(R.id.pressureVal);
        windsVal = (TextView) findViewById(R.id.windsVal);
        feelsLikeVal = (TextView) findViewById(R.id.feelsLikeVal);
        dewPointVal = (TextView) findViewById(R.id.dewPointVal);
        cloudsVal = (TextView) findViewById(R.id.cloudsVal);
        imgView = (ImageView) findViewById(R.id.cloudImageView);
    }

    public void getMinMaxTemp() {
        Weather thisObj;
        Calendar curCalender = new GregorianCalendar();
        curCalender.setTime(forecast.getTimeStamp());
        Calendar thisCalender = new GregorianCalendar();

        for(int i = 0; i < weatherList.size(); i++) {
            thisObj = weatherList.get(i);
            thisCalender.setTime(thisObj.getTimeStamp());
            if((curCalender.get(Calendar.YEAR)== thisCalender.get(Calendar.YEAR)) && (curCalender.get(Calendar.MONTH)== thisCalender.get(Calendar.MONTH)) && (curCalender.get(Calendar.DAY_OF_MONTH)== thisCalender.get(Calendar.DAY_OF_MONTH))) {
                if(thisObj.getTemperature() > maxTemp){
                    maxTemp = thisObj.getTemperature();
                }
                if(thisObj.getTemperature() < minTemp) {
                    minTemp = thisObj.getTemperature();
                }
            }
        }
    }

    public void setData() {
        Date dt = forecast.getTimeStamp();
        locationDetails.setText(city + ", " + state + "(" + dateFormat.format(dt) + ")");
        temperatureVal.setText(String.valueOf(forecast.getTemperature()) + (char) 0x00B0 + "F");
        cloudDescVal.setText(forecast.getClimateType());
        minTempView.setText(String.valueOf(minTemp) + " Fahrenheit");
        maxTempView.setText(String.valueOf(maxTemp) + " Fahrenheit");
        humidityVal.setText(String.valueOf(forecast.getHumidity()) + "%");
        pressureVal.setText(String.valueOf(forecast.getPressure()) + " hPa");
        windsVal.setText(String.valueOf(forecast.getWindSpeed()) + " mph, " + forecast.getDegrees() + " " + getDirection(forecast.getWindDir()));
        feelsLikeVal.setText(String.valueOf(forecast.getFeelsLike()) + " Fahrenheit");
        dewPointVal.setText(String.valueOf(forecast.getDewPoint()) + " Fahrenheit");
        cloudsVal.setText(forecast.getClouds());
        Picasso.with(this).load(forecast.getIcon_url()).into(imgView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addCity) {
            if(weatherList.size() > 0) {
                int index = isFavCityAlreadyExists(city, state);
                if(index == -1) {
                    favList.add(new FavouriteCity(weatherList.get(0).getTimeStamp(), city, state, weatherList.get(0).getTemperature()));
                    Toast.makeText(this, R.string.fav_added, Toast.LENGTH_LONG).show();
                } else {
                    FavouriteCity fav = favList.get(index);
                    fav.setTemperature(weatherList.get(0).getTemperature());
                    fav.setDate(weatherList.get(0).getTimeStamp());
                    Toast.makeText(this, R.string.fav_updated, Toast.LENGTH_LONG).show();
                }
                favCitiesStr = gson.toJson(favList,MainActivity.type);
                editor.clear();
                editor.putString(MainActivity.PREF_KEY_NAME,favCitiesStr);
                editor.commit();
            }
        }
        return true;
    }

    public int isFavCityAlreadyExists(String city, String state) {
        int index = -1;
        favCitiesStr = pref.getString(MainActivity.PREF_KEY_NAME,null);
        favList = gson.fromJson(favCitiesStr, MainActivity.type);
        for(int i = 0; i < favList.size(); i++) {
            if(favList.get(i).getCity().toString().equals(city) && favList.get(i).getState().equals(state)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public String getDirection(String dir) {
        switch(dir) {
            case "N":
                return("North");
            case "E":
                return("East");
            case "W":
                return("West");
            case "S":
                return("South");
            default:
                return(dir);
        }
    }
}
