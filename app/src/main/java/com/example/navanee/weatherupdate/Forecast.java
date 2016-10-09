package com.example.navanee.weatherupdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Forecast extends AppCompatActivity {
    TextView locationDetails, temperatureVal, cloudDescVal, minTempView, maxTempView, humidityVal, pressureVal, windsVal, feelsLikeVal, dewPointVal, cloudsVal;
    ImageView imgView;
    Weather forecast;
    String city, state;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
    ArrayList<Weather> weatherList;
    int minTemp = 200, maxTemp = 0;
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
        temperatureVal.setText(String.valueOf(forecast.getTemperature()));
        cloudDescVal.setText(forecast.getClimateType());
        minTempView.setText(String.valueOf(minTemp));
        maxTempView.setText(String.valueOf(maxTemp));
        humidityVal.setText(String.valueOf(forecast.getHumidity()));
        pressureVal.setText(String.valueOf(forecast.getPressure()));
        windsVal.setText(String.valueOf(forecast.getWindSpeed()));
        feelsLikeVal.setText(String.valueOf(forecast.getFeelsLike()));
        dewPointVal.setText(String.valueOf(forecast.getDewPoint()));
        cloudsVal.setText(forecast.getClouds());
        Picasso.with(this).load(forecast.getIcon_url()).into(imgView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
