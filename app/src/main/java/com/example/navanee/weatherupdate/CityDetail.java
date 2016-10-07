package com.example.navanee.weatherupdate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CityDetail extends AppCompatActivity implements GetData.DataRetreiver {

    String city;
    String state;
    TextView locationDet;
    ListView weatherListView;
    LinearLayout weatherListLayout;
    RelativeLayout weatherLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        setAllViews();
        city = getIntent().getExtras().get("city").toString();
        state = getIntent().getExtras().get("state").toString();
        String dataUrl = R.string.data_url;
        dataUrl = dataUrl.replace("state_name",state);
        dataUrl = dataUrl.replace("city_name",city);

        new GetData(this).execute(dataUrl);
    }

    public void setAllViews() {
        locationDet = (TextView) findViewById(R.id.locationDetails);
        weatherListView = (ListView) findViewById(R.id.weatherList);
        weatherListLayout = (LinearLayout) findViewById(R.id.weatherListLayout);
        weatherLoadingLayout = (RelativeLayout) findViewById(R.id.weatherLoadingLayout);
    }

    @Override
    public void setData(ArrayList<Weather> weatherList) {
        if(weatherList.size() == 0) {
            Toast.makeText(this,R.string.toast_noRecordFound,Toast.LENGTH_LONG);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Records Exist",Toast.LENGTH_LONG);
        }
    }
}
