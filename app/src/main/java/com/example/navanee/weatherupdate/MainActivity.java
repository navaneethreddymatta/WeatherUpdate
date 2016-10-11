package com.example.navanee.weatherupdate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView cityName, stateName;
    ListView favListView;
    Button submitButton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String favCitiesStr;
    Gson gson = new Gson();
    LinearLayout favListLayout, noFavListLayout;
    List<FavouriteCity> favList;

    static String PREF_NAME = "favCities";
    static String PREF_KEY_NAME = "favCitiesList";
    static Type type = new TypeToken<List<FavouriteCity>>(){}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllViews();
        if(isNetworkConnected()){
            submitButton.setOnClickListener(this);
            showFavTableUI();
        } else {
            Toast.makeText(this, R.string.noConnection, Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void setAllViews() {
        cityName = (TextView) findViewById(R.id.cityName);
        stateName = (TextView) findViewById(R.id.stateName);
        submitButton = (Button) findViewById(R.id.submitBtn);
        favListView = (ListView) findViewById(R.id.favListView);
        favListLayout = (LinearLayout) findViewById(R.id.favListLayout);
        noFavListLayout = (LinearLayout) findViewById(R.id.noFavListLayout);
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
        if(city == null || city.trim().equals("")) {
            Toast.makeText(this, R.string.toast_noCityName, Toast.LENGTH_LONG).show();
            return;
        }
        else if(state == null || state.trim().equals("")) {
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

    public void showFavTableUI() {
        pref = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        editor = pref.edit();
        favCitiesStr = pref.getString(PREF_KEY_NAME,null);
        if(favCitiesStr == null) {
            favCitiesStr = "[]";
            editor.putString(PREF_KEY_NAME,favCitiesStr);
            editor.commit();
        }
        favList = new Gson().fromJson(favCitiesStr,type);
        if(favList.size() > 0) {
            noFavListLayout.setVisibility(View.GONE);
            favListLayout.setVisibility(View.VISIBLE);
            FavouritesAdapter favAdapter = new FavouritesAdapter(this,R.layout.favourites_row_layout,favList);
            favListView.setAdapter(favAdapter);
            favAdapter.setNotifyOnChange(true);
            favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,CityDetail.class);
                    intent.putExtra("city",favList.get(position).getCity());
                    intent.putExtra("state",favList.get(position).getState());
                    startActivity(intent);
                }
            });
            favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    favList = gson.fromJson(favCitiesStr, MainActivity.type);
                    favList.remove(position);
                    Toast.makeText(MainActivity.this,R.string.toast_recordDeleted,Toast.LENGTH_LONG).show();
                    favCitiesStr = gson.toJson(favList,type);
                    editor.putString(PREF_KEY_NAME,favCitiesStr);
                    editor.commit();
                    showFavTableUI();
                    return true;
                }
            });
        } else {
            favListLayout.setVisibility(View.GONE);
            noFavListLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showFavTableUI();
    }
}
