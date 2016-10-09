package com.example.navanee.weatherupdate;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView cityName;
    TextView stateName;
    Button submitButton;
    static ArrayList<FavouriteCity> favCities = new ArrayList<FavouriteCity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllViews();
        if(isNetworkConnected()){
            submitButton.setOnClickListener(this);
        } else {
            Toast.makeText(this, R.string.noConnection, Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void setAllViews() {
        cityName = (TextView) findViewById(R.id.cityName);
        stateName = (TextView) findViewById(R.id.stateName);
        submitButton = (Button) findViewById(R.id.submitBtn);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni.isConnected();
    }

    @Override
    public void onClick(View v) {
        String city = cityName.getText().toString();
        String state = stateName.getText().toString();
        if(city == null || city.trim() == "") {
            Toast.makeText(this, R.string.toast_noCityName, Toast.LENGTH_LONG).show();
            return;
        }
        else if(state == null || state.trim() == "") {
            Toast.makeText(this, R.string.toast_noStateName, Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Intent intent = new Intent(this, CityDetail.class);
            intent.putExtra("city",city);
            intent.putExtra("state",state);
            startActivity(intent);
        }
    }
}
