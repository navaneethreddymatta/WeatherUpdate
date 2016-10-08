package com.example.navanee.weatherupdate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Forecast extends AppCompatActivity {
    TextView locationDetails, temperatureVal, cloudDescVal, minTemp, maxTemp, humidityVal, pressureVal, windsVal, feelsLikeVal, dewPointVal, cloudsVal;
    ImageView imgView;
    Weather forecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        setAllViews();
        setData();
    }

    public void setAllViews() {
        locationDetails = (TextView) findViewById(R.id.locationDetails);
        temperatureVal = (TextView) findViewById(R.id.temperatureView);
        cloudDescVal = (TextView) findViewById(R.id.cloudView);
        minTemp = (TextView) findViewById(R.id.minTempVal);
        maxTemp = (TextView) findViewById(R.id.maxTempVal);
        humidityVal = (TextView) findViewById(R.id.humidityVal);
        pressureVal = (TextView) findViewById(R.id.pressureVal);
        windsVal = (TextView) findViewById(R.id.windsVal);
        feelsLikeVal = (TextView) findViewById(R.id.feelsLikeVal);
        dewPointVal = (TextView) findViewById(R.id.dewPointVal);
        cloudsVal = (TextView) findViewById(R.id.cloudsVal);
        imgView = (ImageView) findViewById(R.id.cloudImageView);
    }

    public void setData() {
        forecast = (Weather) getIntent().getExtras().get("forecast");
        locationDetails.setText("");
        temperatureVal.setText("");
        cloudDescVal.setText("");
        minTemp.setText("");
        maxTemp.setText("");
        humidityVal.setText("");
        pressureVal.setText("");
        windsVal.setText("");
        feelsLikeVal.setText("");
        dewPointVal.setText("");
        cloudsVal.setText("");
        Picasso.with(this).load(forecast.getIcon_url()).into(imgView);
    }
}
