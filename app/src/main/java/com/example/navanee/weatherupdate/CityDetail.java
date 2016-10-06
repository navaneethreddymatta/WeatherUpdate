package com.example.navanee.weatherupdate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CityDetail extends AppCompatActivity {

    String city;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        setAllViews();
        city = getIntent().getExtras().get("city").toString();
        state = getIntent().getExtras().get("state").toString();
    }

    public void setAllViews() {

    }
}
